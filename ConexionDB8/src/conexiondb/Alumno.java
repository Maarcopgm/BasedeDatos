package conexiondb;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Alumno extends Persona {

    private String matricula;
    
    public Alumno(int id)
    {
        Conexion con=new Conexion();         
        con.conectate();        
        Connection conx=con.getConn();
               
        try
        {
                Statement st = conx.createStatement();
                ResultSet res = st.executeQuery("SELECT * FROM  alumno WHERE id="+String.valueOf(id));

                if (res.next()) {

                    this.setId(res.getInt("idalumno"));
                    this.setNombre(res.getString("nombre"));
                    this.setApellidoPaterno(res.getString("apellido_paterno"));
                    this.setApellidoMaterno(res.getString("apellido_materno"));
                    this.setDireccion(res.getString("direccion"));
                    this.matricula=res.getString("matricula");
                    this.setFechaNacimiento(res.getString("fecha_nacimiento"));         

               }
          conx.close();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }     
          
    }
    
    public Alumno(String matricula,String nombre,String apellidoPaterno,String apellidoMaterno,String fechaNacimiento,String direccion)
    {
        this.setNombre(nombre);
        this.setApellidoPaterno(apellidoPaterno);
        this.setApellidoMaterno(apellidoMaterno);
        this.setDireccion(direccion);
        this.matricula=matricula;
        this.setFechaNacimiento(fechaNacimiento);  
        
    }
      
    public String getMatricula() {
        return matricula;
    }   
   
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    @Override
    public int crear()
    {
        int retorno=0;
        Conexion con=new Conexion();         
        con.conectate();        
        Connection conx=con.getConn();
               
        try
        {
                Statement st = conx.createStatement();
                String sqlInsert="INSERT INTO Alumno VALUES (default,?,?,?,?,?,?);";
                PreparedStatement pstmt=conx.prepareStatement(sqlInsert,PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, this.matricula);
                pstmt.setString(2, this.getNombre());
                pstmt.setString(3, this.getApellidoPaterno());
                pstmt.setString(4, this.getApellidoMaterno());
                pstmt.setString(5, this.getFechaNacimiento());
                pstmt.setString(6, this.getDireccion());
                pstmt.executeUpdate(); 
                
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs != null && rs.next()) {
                     this.setId( rs.getInt(1));
                }
                else
                {
                    retorno=-1;
                }
               
                
                conx.close();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            retorno=-1;
        }
        
        return retorno;
    }
    

}