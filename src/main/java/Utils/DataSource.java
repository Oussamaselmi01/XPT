
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSource {
<<<<<<< HEAD
	private String url = "jdbc:mysql://localhost:3306/experiencetunisiaInteg2";
=======
	private String url = "jdbc:mysql://localhost:3306/experiencetunisiainteg2";
>>>>>>> 732ca04 (api+métier)
	private String username = "root";
	private String password = "";
        private Connection cnx;
        private static DataSource instance;

	
	private DataSource() {
            try {
                cnx= DriverManager.getConnection(url, username, password);
                System.out.println("connexion établie");
            } catch (SQLException ex) {
                Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
        
        
        
        public static DataSource getInstance(){
            if(instance==null)
                instance=new DataSource();
            return instance;  }
        
         public Connection getCnx(){
         return cnx;
         
        }
         
         
}
