using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Configuration;
using System.Web.Configuration;


namespace WCFServiceWebRole1
{
    public class ConfigHelper
    {
        string _sqlAzureConnectionString;
        static ConfigHelper _instance;

        private ConfigHelper()
        {
            //Configuration myConfig = WebConfigurationManager.ConnectionStrings[]OpenWebConfiguration("~");
            //ConnectionStringsSection mySection = myConfig.GetSection("connectionStrings") as ConnectionStringsSection;
            _sqlAzureConnectionString = WebConfigurationManager.ConnectionStrings["DBReportItContainer"].ConnectionString;
                //mySection.ConnectionStrings["DBReportItContainer"].ConnectionString;
        }

        public static ConfigHelper Instance
        {
            get
            {
                if (_instance == null)
                {
                    return _instance = new ConfigHelper();
                }
                else
                {
                    return _instance;
                }
            }
        }

        public string SQLAzureConnectionString
        {
            get
            {
                return this._sqlAzureConnectionString;
            }
            set
            {
                this._sqlAzureConnectionString = value;
            }
        }
    }
}