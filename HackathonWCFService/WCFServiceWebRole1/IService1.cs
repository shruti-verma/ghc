using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace WCFServiceWebRole1
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IService1" in both code and config file together.
    [ServiceContract]
    public interface IReportIt
    {

        [OperationContract]
        int CreateComplaint(Complain value);

        [OperationContract]
        IEnumerable<Complain> GetComplaints(Complain complaint);

        [OperationContract]
        int CreateComplainAction(ComplainAction cAction);

        [OperationContract]
        IEnumerable<ComplainAction> GetComplaintActions(ComplainAction action);
        // TODO: Add your service operations here
    }


    
}
