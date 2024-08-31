
package yumxpress.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import yumxpress.dbutil.DBConnection;
import yumxpress.pojo.CompanyPojo;

public class CompanyDAO {
    public static String getNewId() throws SQLException{
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select max(company_id) from companies");
        rs.next();
        String id = rs.getString(1);
        String compId="";
        if(id!=null){
            id=id.substring(4);
            compId="CMP-"+(Integer.parseInt(id)+1);
        }else{
            compId="CMP-101";
        }
        return compId;
    }
    public static boolean addSeller(CompanyPojo comp) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into companies values(?,?,?,?,?,?,?)");
        ps.setString(1,getNewId());
        ps.setString(2,comp.getCompanyName());
        ps.setString(3,comp.getOwnerName());
        ps.setString(4,comp.getPassword());
        ps.setString(5,"Active");
        ps.setString(6,comp.getEmailId());
        ps.setString(7,comp.getSecurityKey());
        
        return ps.executeUpdate()==1;
        
    }
    public static CompanyPojo validate(String compName,String password)throws SQLException{
       Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from companies where company_name=? and password=? and status='Active'" ); 
        ps.setString(1,compName);
        System.out.println("In C_Dao compName: "+compName);
        ps.setString(2,password);
        System.out.println("Password: "+password);
        ResultSet rs = ps.executeQuery();
        CompanyPojo comp=null;
        if(rs.next()){
            comp = new CompanyPojo();
            comp.setCompanyId(rs.getString(1));
            comp.setOwnerName(rs.getString(3));
            comp.setCompanyName(rs.getString(2));
        }
        return comp;
    }
    public static Map<String,String>getEmailCredentailsByCompanyId(String companyId) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select email_id,security_key from companies where company_id=? and status='Active'" ); 
        ps.setString(1,companyId);
        Map<String,String> companyCredentials=new HashMap<>();
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
            String emailId=rs.getString(1);
            String secKey=rs.getString(2);
            companyCredentials.put("emailId",emailId);
            companyCredentials.put("securityKey",secKey);

        }
        return companyCredentials;
    }
    public static Map<String,String> getAllCompanyIdAndName() throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select company_id,company_name from companies where status='Active' and company_id in (select company_id from products)" ); 
        ResultSet rs = ps.executeQuery();
        Map<String,String> compList=new HashMap<>();
        while(rs.next()){
            String c_id=rs.getString(1);
            String c_name=rs.getString(2);
            compList.put(c_name,c_id);           
        }
        return compList;
    }

   
    
    
}
