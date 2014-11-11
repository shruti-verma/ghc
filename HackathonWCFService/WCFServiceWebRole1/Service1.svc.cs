using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Web.Configuration;
using System.Transactions;
using System.Data.SqlClient;
using System.Data;
using System.Threading.Tasks;
//using WCFServiceWebRole1.DataContracts;


namespace WCFServiceWebRole1
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Service1.svc or Service1.svc.cs at the Solution Explorer and start debugging.
    public class ReportItService : IReportIt
    {
        //SqlConnection sqlConnection = new SqlConnection(ConfigHelper.Instance.SQLAzureConnectionString);
        //DbContext db = new DbContext(ConfigHelper.Instance.SQLAzureConnectionString,
        GHCReportItEntities db;
        DBHelper dbHelper = new DBHelper();
        //SqlConnection sqlConnection = new SqlConnection(ConfigHelper.Instance.SQLAzureConnectionString);

        public int CreateComplaint(Complain value)
        {
            //using (var scope = new TransactionScope(TransactionScopeOption.Required, new TransactionOptions { IsolationLevel = System.Transactions.IsolationLevel.ReadUncommitted }))
            //{
            //    using (GHCReportItEntities db1 = SharedObjContext.Instance.Context)
            //    {
            //        Complain newComplaint = db1.Complain.Add(value);
            //        db1.SaveChanges();
            //        //return RedirectToAction("Index");
            //        return newComplaint.ComplainID;
            //    }
            //}

            using (SqlConnection sqlConnection = new SqlConnection(ConfigHelper.Instance.SQLAzureConnectionString))
            {
                sqlConnection.Open();
                SqlCommand command = new SqlCommand("INSERT INTO [dbo].[Complain] VALUES (" + value.ComplainCategory + ",'" + value.ComplainCategoryValue + "','" + value.UploadFilePath + "','" + value.Location + "','" +
                    value.Description + "','" + value.UserName + "','" + value.UserContactNumber + "', getutcdate(),getutcdate()" + ")", sqlConnection);

                return (int)command.ExecuteScalar();
            }
            //Complain newComplaint = db.Complain.Add(value);
            //db.SaveChanges();
            ////return RedirectToAction("Index");
            //return newComplaint.ComplainID;
        }

        public IEnumerable<Complain> GetComplaints(Complain complaint)
        {
            List<Complain> complaints = null;

            using (SqlConnection sqlConnection = new SqlConnection(ConfigHelper.Instance.SQLAzureConnectionString))
            {
                sqlConnection.Open();
                string commandTxt = "SELECT * FROM [dbo].[Complain]";

                string whereClause = string.Empty;
                if (complaint.ComplainCategory > 0)
                {
                    whereClause += " ComplainCategory = " + complaint.ComplainCategory;
                    //complaints = db.Complain.Where<Complain>(q => q.ComplainCategory == complaint.ComplainCategory);
                }
                else if (!String.IsNullOrEmpty(complaint.UserName))
                {
                    whereClause += " UserName = '" + complaint.UserName + "'";
                    //db.Complain.Where<Complain>(q => q.UserName == complaint.UserName);
                }

                if (!string.IsNullOrEmpty(complaint.Description))
                {
                    if(!string.IsNullOrEmpty(whereClause))
                        whereClause += " and ";
                    
                    whereClause += " description like '" + complaint.Description + "'";
                }

                commandTxt += " WHERE " + whereClause;
                SqlCommand command = new SqlCommand(commandTxt, sqlConnection);
                DataTable dt;
                using (var reader = command.ExecuteReader(CommandBehavior.CloseConnection))
                {
                    dt = new DataTable();
                    dt.Load(reader);
                }

                if (dt == null || dt.Rows == null || dt.Rows.Count == 0)
					return new List<Complain>();
				
				var result = new List<Complain>();

				Parallel.ForEach(dt.AsEnumerable(), new ParallelOptions
				{
					MaxDegreeOfParallelism = 1
				}, dr =>
				{
					var obj = dbHelper.TransformToComplain(dr);
					if (obj == null)
						return;
					lock (complaints)
					{
						complaints.Add(obj);
					}
				});
				
                return complaints;
            }
        }


        public int CreateComplainAction(ComplainAction cAction)
        {
            //ComplainAction action = db.ComplainAction.Add(cAction);
            //db.SaveChanges();
            using (SqlConnection sqlConnection = new SqlConnection(ConfigHelper.Instance.SQLAzureConnectionString))
            {
                sqlConnection.Open();
                SqlCommand command = new SqlCommand("INSERT INTO [dbo].[ComplainAction] VALUES (" + cAction.ComplaintID + "," + cAction.ActionType + ",'" + cAction.ActionForContact + "','" + cAction.Description + "'" +
                    ", getutcdate(),getutcdate()" + ")", sqlConnection);

                return command.ExecuteNonQuery();
            }

        }

        public IEnumerable<ComplainAction> GetComplaintActions(ComplainAction cAction)
        {
            List<ComplainAction> complaintActions = null;

            using (SqlConnection sqlConnection = new SqlConnection(ConfigHelper.Instance.SQLAzureConnectionString))
            {
                sqlConnection.Open();
                string commandTxt = "SELECT * FROM [dbo].[ComplainAction]";

                if (cAction.ComplaintID > 0)
                {
                    commandTxt += " WHERE ComplaintID = " + cAction.ComplaintID;
                    //complaints = db.Complain.Where<Complain>(q => q.ComplainCategory == complaint.ComplainCategory);
                }
                
                SqlCommand command = new SqlCommand(commandTxt, sqlConnection);
                DataTable dt;
                using (var reader = command.ExecuteReader(CommandBehavior.CloseConnection))
                {
                    dt = new DataTable();
                    dt.Load(reader);
                }

                if (dt == null || dt.Rows == null || dt.Rows.Count == 0)
                    return new List<ComplainAction>();

                var result = new List<ComplainAction>();

                Parallel.ForEach(dt.AsEnumerable(), new ParallelOptions
                {
                    MaxDegreeOfParallelism = 1
                }, dr =>
                {
                    var obj = dbHelper.TransformToComplainAction(dr);
                    if (obj == null)
                        return;
                    lock (complaintActions)
                    {
                        complaintActions.Add(obj);
                    }
                });

                return complaintActions;
            }
        }
    }
}
