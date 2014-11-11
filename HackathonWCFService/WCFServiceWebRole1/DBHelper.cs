using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;

namespace WCFServiceWebRole1
{
    public class DBHelper
    {

        public Complain TransformToComplain(DataRow dr)
        {
            Complain complain = new Complain();
            complain.ComplainID = (dr["ComplainID"] != null) ? (int)dr["ComplainID"] : -1;
            complain.ComplainCategory = (dr["ComplainCategory"] != null) ? (int)dr["ComplainCategory"] : -1;
            complain.ComplainCategoryValue = dr["ComplainCategoryValue"] != null ? (string)dr["ComplainCategoryValue"] : null;
            complain.Description = (dr["Description"] != null) ? (string)dr["Description"] : null;
            complain.Location = (dr["Location"] != null) ? (string)dr["Location"] : null;
            complain.UploadFilePath = (dr["UploadFilePath"] != null) ? (string)dr["UploadFilePath"] : null;
            complain.UserContactNumber = (dr["UserContactNumber"] != null) ? (string)dr["UserContactNumber"] : null;
            complain.UserName = (dr["UserName"] !=null) ? (string)dr["UserName"] : null;
            complain.ModifiedDate = (DateTime)dr["ModifiedDate"];
            complain.CreatedDate = (DateTime)dr["CreatedDate"];
            return complain;
        }

        public ComplainAction TransformToComplainAction(DataRow dr)
        {
            ComplainAction complainAction = new ComplainAction();
            complainAction.ComplaintID = (dr["ComplaintID"] != null) ? (int)dr["ComplaintID"] : -1;
            complainAction.ActionType = (dr["ActionType"] != null) ? (int)dr["ActionType"] : -1;
            complainAction.Description = (dr["Description"] != null) ? (string)dr["Description"] : null;
            complainAction.ActionForContact = (dr["ActionForContact"] != null) ? (string)dr["ActionForContact"] : null;
            complainAction.ModifiedOn = (DateTime)dr["ModifiedOn"];
            complainAction.CreatedOn = (DateTime)dr["ModifiedOn"];
            return complainAction;
        }

    }
}