using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WCFServiceWebRole1
{
    public class SharedObjContext
    {
        private readonly GHCReportItEntities context;
    
        #region Singleton Pattern

        // Static members are lazily initialized.
        // .NET guarantees thread safety for static initialization.
        private static readonly SharedObjContext instance = new SharedObjContext();

        // Make the constructor private to hide it. 
        // This class adheres to the singleton pattern.
        private SharedObjContext()
        {
            // Create the ObjectContext.
            context = new GHCReportItEntities();
        }

        // Return the single instance of the ClientSessionManager type.
        public static SharedObjContext Instance
        {
            get
            {
                return instance;
            }
        }   

        #endregion

        public GHCReportItEntities Context
        {
            get
            {
                return context;
            }
        }

    }
}