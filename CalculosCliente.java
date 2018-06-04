/*
 */
package Cliente;

//librerias
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Metodos.ConexionBBDD;
import java.util.List;
import javax.swing.JButton;

/**
 * @author dbeato
 */
public class CalculosCliente {

    //Estanciamos la clase ConexionBBDD y la llamamos select ( que tiene la conexion y para poder lanzar select)
    ConexionBBDD select = new ConexionBBDD();

    String apellido,nombre,fechaNacimiento,sexoL,ciudad,domicilio,postal,movil,mail,observacion,fijoTelefono,entidad,cuentaBancaria;
    String centroJ = "",actividadJ = "",diasJ = "",horaA = "",minA = "",horaF = "",minF = "",totalSQL = "",totalInsertar = "";

    DefaultListModel<String> creacionClases = new DefaultListModel<>();
    DefaultListModel<String> busquedaClientes = new DefaultListModel<>();
    DefaultListModel<String> busquedaClases = new DefaultListModel<>();
            
    DefaultListModel<String> model = new DefaultListModel<>();

    //SQL
    PreparedStatement prepare;
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
   
    //LOGIN
    String driver = "org.gjt.mm.mysql.Driver";
    String conBBDD = "jdbc:mysql://olan.sytes.net:3306/gim";
    String usuarioBBDD = "nalo";
    String passBBDD = "nalo";

    //SENTENCIAS
    String altaCliente = "INSERT INTO clientes (nombre,apellido,sexo,ciudad,domicilio,cPostal,mail,observaciones,fechaNacimiento,telefonoMovil,telefonoFijo,entidad,nCuenta,tipoPago) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String altaProfesor = "INSERT INTO profesores (nombre,apellido,sexo,ciudad,domicilio,cPostal,mail,observaciones,fechaNacimiento,telefonoMovil,telefonoFijo) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    String crearClase = " INSERT INTO clase (actividad,centro,dias,horaEntrada,minutosEntrada,horaSalida,minutosSalida,precio,idProfesor)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String insertarClaseEnAlumno = "UPDATE clientes SET clase = ? ";
    String clases = "SELECT clase FROM clientes WHERE nAlumno =";
    
    String TotalSentencias = "";
    
    //Proximos a usar
    String insertarPago = "INSERT INTO pagos (anio,mes,idClase,precio,idUsuario,idProfesor)"+"VALUES (?,?,?,?,?,?)";
    String cogerProfesorClase = "SELECT idProfesor FROM clase WHERE idClase = ";

    //PARAMETRO SENTENCIAS
    int ID,idClase,resultado = 0;
    String nombreRS,apellidoRS,diasAlumno,centroAlumno,disciplinaAlumno;

    boolean and = false;
    boolean and2 = false;
    boolean and3 = false;

    String actividad,centro,hEntrada,mEntrada,hSalida,mSalida;
    String profesor,disciplina,pabellon,dias = "",horaEntrada,minEntrada,horaSalida,minSalida,precio;
    String totalAlumno,totalClase,claseQuePosee,nClase,idProfesor;
    String[] splits;
    String[] splits2;
    
    String mesPago,temporadaPago,precioPago;


    //Insertar Alumnos en la BBDD Cliente Le pasamos los Jtext y el resto de cosas para pillar sus valores actuales // DONE \\
    public void insertCliente(JTextField nombreTX, JTextField apellidoTX, String sexoTX, JTextField ciudadTX, JTextField domicilioTX, JTextField cPostal, JTextField mailTX,
            JTextField observacionesTX, JTextField fechaNacimientoTX, JTextField movilTX, JTextField fijoTX, JTextField entidadTX, JTextField nCuentaTX, String tipoPagoTx) throws SQLException, ClassNotFoundException {

        //Asignamos cada valor de los textos y demas a las variables locales
        nombre = nombreTX.getText();
        apellido = apellidoTX.getText();
        //--Sexo--\\
        ciudad = ciudadTX.getText();
        domicilio = domicilioTX.getText();
        postal = cPostal.getText();
        mail = mailTX.getText();
        observacion = observacionesTX.getText();
        fechaNacimiento = fechaNacimientoTX.getText();
        movil = movilTX.getText();
        fijoTelefono = fijoTX.getText();
        entidad = entidadTX.getText();
        cuentaBancaria = nCuentaTX.getText();
        //--tipoPago--\\
        //--tipoCliente--\\
        
        //Llamamos a los estamentos para la conexion los drivers para Xampp y la conexion con la BBDD
        Class.forName(driver);
        //Creación de la conexion con la url necesaria
        //Usando los diverManager creamos la conexion pasandole direccion bbdd usuario y pass
        con = DriverManager.getConnection(conBBDD, usuarioBBDD, passBBDD);
        //Sobre la conexion creamos los estamentos y le pasamos altaCliente
        prepare = con.prepareStatement(altaCliente);

        //Asignamos a prepare el tipo que es en la bbdd que posicion de "?" ocupa y que valor de variable tiene
        prepare.setString(1, nombre);
        prepare.setString(2, apellido);
        prepare.setString(3, sexoTX);
        prepare.setString(4, ciudad);
        prepare.setString(5, domicilio);
        prepare.setString(6, postal);
        prepare.setString(7, mail);
        prepare.setString(8, observacion);
        prepare.setString(9, fechaNacimiento);
        prepare.setString(10, movil);
        prepare.setString(11, fijoTelefono);
        prepare.setString(12, entidad);
        prepare.setString(13, cuentaBancaria);
        prepare.setString(14, tipoPagoTx);

        //Ejecutamos la sentencia e insertamos esos valores
        prepare.executeUpdate();

        //Cerramos la conexion
        prepare.close();

    }

    //Insertar en Profesores un profesor pillando todos los datos de profesor // DONE \\
    public void insertProfesor(JTextField nombreTX, JTextField apellidoTX, String sexoTX, JTextField ciudadTX, JTextField domicilioTX, JTextField cPostal, JTextField mailTX,
            JTextField observacionesTX, JTextField fechaNacimientoTX, JTextField movilTX, JTextField fijoTX) throws SQLException, ClassNotFoundException {

        nombre = nombreTX.getText();
        apellido = apellidoTX.getText();
        //--Sexo--\\
        ciudad = ciudadTX.getText();
        domicilio = domicilioTX.getText();
        postal = cPostal.getText();
        mail = mailTX.getText();
        observacion = observacionesTX.getText();
        fechaNacimiento = fechaNacimientoTX.getText();
        movil = movilTX.getText();
        fijoTelefono = fijoTX.getText();

        Class.forName(driver);
        //Creación de la conexion con la url necesaria
        //Creación de la conexion con la url necesaria
        //Usando los diverManager creamos la conexion pasandole direccion bbdd usuario y pass
        con = DriverManager.getConnection(conBBDD, usuarioBBDD, passBBDD);
        //Sobre la conexion creamos los estamentos y le pasamos altaPRofesor
        prepare = con.prepareStatement(altaProfesor);

        //Asignamos a prepare el tipo que es en la bbdd que posicion de "?" ocupa y que valor de variable tiene
        prepare.setString(1, nombre);
        prepare.setString(2, apellido);
        prepare.setString(3, sexoTX);
        prepare.setString(4, ciudad);
        prepare.setString(5, domicilio);
        prepare.setString(6, postal);
        prepare.setString(7, mail);
        prepare.setString(8, observacion);
        prepare.setString(9, fechaNacimiento);
        prepare.setString(10, movil);
        prepare.setString(11, fijoTelefono);

        //Ejecutamos la sentencia e insertamos esos valores
        prepare.executeUpdate();

        //Cerramos la conexion
        prepare.close();
    }

    //Busqueda por apellido y mostrar resultado || Le pasamos la query | Busqueda que sera el txt escrito en el apartado | La lista de los profesores//DONE\\
    public void mostrarProfesoresApellido(String query, String busqueda, JList<String> listaProfesores) throws ClassNotFoundException, SQLException {
        //Ponemos totalSentencia a 0 para poder lanzarla vacia
        TotalSentencias = "";
        //Sera igual a Query + el parametro de busqueda que pillemos del texto
        TotalSentencias = query + busqueda + "%' ";
        
        //llamamos al metodo select estanciaciado arrriba y le pasamos el total de la query que creamos
        rs = select.select(TotalSentencias);

        //Limpiamos el Jlist para mostrar los nuevos resultados
        creacionClases.clear();
        //Asignamos la lista por defecto ( que vcaciamos a la nuestra)
        listaProfesores.setModel(creacionClases);

        //El select nos devolvera un ResultSet (rs) con todos los resultados de la sentencia los recorremos mientras tenga un siguiente
        while (rs.next()) {

            //Asignamos el valor recogido (de la BBDD) llamado nProfesor que es de tipo int a nuestra ID local de tipo int
            ID = rs.getInt("nProfesor");
            //Asignamos el valor recogido  (de la BBDD) llamado nombre que es de tipo string a nuestra variable nombre de tipo string
            nombre = rs.getString("nombre");
            //Asignamos el valor recogido  (de la BBDD) llamado apellido que es de tipo String a nuestra variable apellido de tipo String
            apellido = rs.getString("apellido");
                  
            //Con cada resultado añadimos al Default List - el resultado obtenido separando la ID con # para facilitarnos su corte
            creacionClases.addElement(ID + "#" + nombre + " " + apellido);

        }
        //Cuando termina rs de recorrerse y de meterse todos los resultados en creacion clase ( DefaultModel) asignamos ese defaultMode a nuestro Jlist para que muestre los resultados
        listaProfesores.setModel(creacionClases);

    }

    //Busqueda por ID y mostrar resultado en el Jlist//DONE\\
    public void mostrarProfesoresId(String query, String busqueda, JList<String> listaProfesores) throws ClassNotFoundException, SQLException {
        //Sentencia a 0
        TotalSentencias = "";
        //Pillamos la id pasada por parametro
        int numero = Integer.parseInt(busqueda);
        //creamos la sentencia completa añadiendo el numero que pillamos
        TotalSentencias = query + numero;
        //Lanzamos la select pasandole el total de la setencia y recogeremos un RS ( ResultSet)
        rs = select.select(TotalSentencias);
        //Limpiamos su DefaultList para usarla
        creacionClases.clear();
        //Asignamos el DegaulList para vaciarlo
        listaProfesores.setModel(creacionClases);

        //Recorremos los resultados recogiendo los datos de la sentencia RS
        while (rs.next()) {

            ID = rs.getInt("nProfesor");
            nombre = rs.getString("nombre");
            apellido = rs.getString("apellido");

            //añadimos los resultados al default list  # para poder cortar por id
            creacionClases.addElement(ID + "#" + nombre + " " + apellido);

        }
        //Asignamos la lista que creamos con RS a nuestra lista
        listaProfesores.setModel(creacionClases);

    }

    //Creacion de Clase Asignada a profesor\\ //DONE\\
    public void insertarClase(JList<String> listaProfesores, JList<String> disciplinaClase, JList<String> pabellonClase, JList<String> diasClase, JSpinner horaEntradaClase, JSpinner horaSalidaClase, JSpinner minutosEntradaClase, JSpinner minutosSalidaClase, JTextField precioClase) throws ClassNotFoundException, SQLException {
        //Valor a 0 de dias;
        dias = "";            
        //Pillamos el valor de la lista profesores
        profesor = listaProfesores.getSelectedValue();
        //Cortamos por # para solo sacar la id
        String[] corte = profesor.split("#");
        //Asignamos el valor del recorte en la posicion 0 ( la ID del profesor)
        ID = Integer.parseInt(corte[0]);
        
        //Seleccionamos la disciplina marcada del jlist ( Judo, Karate o lo que sea)
        disciplina = disciplinaClase.getSelectedValue();
        //Seleccionamos el pabellon marcado del Jlist(Europa, victor pradera)
        pabellon = pabellonClase.getSelectedValue();
        //Seleccionamo los dias marcados del Jlist ( Lunes martes,)
        dias = diasClase.getSelectedValue();
        
        //En el caso de que sean varios dias los que marcamos pillara la lista de los marcados
        List<String> ListaDias = diasClase.getSelectedValuesList();
        //Recorremos la lista marcada la metemos en dias separados por , como aparecen en la bbdd
        for (int i = 1; i < ListaDias.size(); i++) {
            dias = dias + "," + ListaDias.get(i);
        }

        //Pillamos el valor de los Spinners que esten marcados
        horaEntrada = horaEntradaClase.getValue().toString();
        minEntrada = minutosEntradaClase.getValue().toString();
        horaSalida = horaSalidaClase.getValue().toString();
        minSalida = minutosSalidaClase.getValue().toString();
        precio = precioClase.getText();

        //LLlamamos a la clase de los drivers y la conexion con usuario y pw
        Class.forName(driver);
        con = DriverManager.getConnection(conBBDD, usuarioBBDD, passBBDD);

        //Creamos el insert desde la sentencia crearClase
        prepare = con.prepareStatement(crearClase);
        //El primer valor de ? 
        prepare.setString(1, disciplina);
        //2
        prepare.setString(2, pabellon);
        prepare.setString(3, dias);
        prepare.setString(4, horaEntrada);
        prepare.setString(5, minEntrada);
        prepare.setString(6, horaSalida);
        prepare.setString(7, minSalida);
        prepare.setString(8, precio);
        prepare.setInt(9, ID);

        // Ejecutar el insert
        prepare.execute();
        //Cerramos la conexion
        con.close();

    }

    //Busqueda por apellido y mostrar resultadoAlumnos\\ // DONE \\
    public void mostrarAlumnosApellido(String query, String busqueda, JList<String> listaAlumnos) throws ClassNotFoundException, SQLException {
        //Dejamos total sentencia vacia
        TotalSentencias = "";
        //Unificamos query y la variable a buscar
        TotalSentencias = query + busqueda + "%' ";
        //Devolvera un RS
        rs = select.select(TotalSentencias);

        //Limpiamos DefaultList
        busquedaClientes.clear();
        listaAlumnos.setModel(busquedaClientes);

        //Recorremos RS asignando cada valor a variables locales
        while (rs.next()) {

            ID = rs.getInt("nAlumno");
            nombre = rs.getString("nombre");
            apellido = rs.getString("apellido");

            //Escribimos en DefaulList # para poder recortar
            busquedaClientes.addElement(ID + "#" + nombre + " " + apellido);

        }
        //Asignamos el DefaultList a nuestra JList
        listaAlumnos.setModel(busquedaClientes);

    }

    //Busqueda por ID y mostrar resultadoAlumnos\\ //DONE\\
    public void mostrarAlumnosId(String query, String busqueda, JList<String> listaAlumnos) throws ClassNotFoundException, SQLException {
        //Ponemos a 0 Sentencia
        TotalSentencias = "";
        //Query + valor de Busqueda 
        TotalSentencias = query + busqueda;
        //Lanzamos la Sentencia para recoger RS
        rs = select.select(TotalSentencias);

        //Limpiamos DEfaultList
        busquedaClientes.clear();
        listaAlumnos.setModel(busquedaClientes);

        //Recorremos RS
        while (rs.next()) {
            //Asignamos cada valor del rs a nuestra variables locales
            ID = rs.getInt("nAlumno");
            nombre = rs.getString("nombre");
            apellido = rs.getString("apellido");
            //Metemos el valor en nuestro defaullist
            busquedaClientes.addElement(ID + "#" + nombre + " " + apellido);

        }
        //
        listaAlumnos.setModel(busquedaClientes);

    }

    //Busqueda de Clase Por filtro\\ //DONE\\
    public void busquedaClases(String query, JList<String> diasAlumnoClase, JList<String> centroAlumnoClase, JList<String> disciplinaAlumnoClase, JList<String> resultadoFiltro) throws ClassNotFoundException, SQLException {
        //TotalSentencia vacio
        TotalSentencias = "";
        
        //Si la seleccion de dias de clase esta vacia no hcer nada en caso de que si que este seleccionado 
        if (diasAlumnoClase.isSelectionEmpty()) {

        } else {
            //Pillar el valor de  dias aulumno y añadirlo a la query
            diasAlumno = diasAlumnoClase.getSelectedValue();
            TotalSentencias = query + "WHERE `dias` LIKE '%" + diasAlumno + "%'";
            
            //Esta Boolean activara la 2 parte de la Query añadir AND en caso de no estar true no se ñadira
            and = true;
        }

        
        if (centroAlumnoClase.isSelectionEmpty()) {

        } else {
            //Si la parte de centro esta marcada se lanzara solo la sentencia sobre el centro
            centroAlumno = centroAlumnoClase.getSelectedValue();
            //si And esta en False.. no entro en la primera por lo que no lanzamos AND si no WHERE.. en el caso de que si este marcado añadiremos AND a la sentencia
            if (!and) {
                TotalSentencias = query + "WHERE `centro` = '" + centroAlumno + "'";
            } else {
                TotalSentencias = TotalSentencias + " AND " + "`centro` = '" + centroAlumno + "'";
                and2 = true;
            }
        }
        //Si la disciplina esta marcada se lanzara solo la sentencia sobre el centro
        if (disciplinaAlumnoClase.isSelectionEmpty()) {

        } else {
            disciplinaAlumno = disciplinaAlumnoClase.getSelectedValue();
            //si And esta en False.. no entro en la primera por lo que no lanzamos AND si no WHERE.. en el caso de que si este marcado añadiremos AND a la sentencia
            if (!and2 || !and) {
                TotalSentencias = query + "WHERE `actividad` = '" + disciplinaAlumno + "'";
            } else {
                TotalSentencias = TotalSentencias + " AND " + "`actividad` = '" + disciplinaAlumno + "'";
            }
        }
        //Borramos defaultlist
        busquedaClases.clear();
        resultadoFiltro.setModel(busquedaClases);

        //lanzamos el select total de la sentencia que haya salido de arriba --
        rs = select.select(TotalSentencias);
        
        //recorreremos el rultado para enseñar las clases que tenemos con todos sus datos
        while (rs.next()) {

            ID = rs.getInt("idClase");
            actividad = rs.getString("actividad");
            centro = rs.getString("centro");
            dias = rs.getString("dias");
            hEntrada = rs.getString("horaEntrada");
            mEntrada = rs.getString("minutosEntrada");
            hSalida = rs.getString("horaSalida");
            mSalida = rs.getString("minutosSalida");
            precio = rs.getString("precio");
            
            //Añadimos la forma en la que queremos que aparezca en el jlist
            busquedaClases.addElement(ID + "#" + actividad + " " + centro + " " + dias + " " + hEntrada + ":" + mEntrada + " | " + hSalida + ":" + mSalida + ", " + precio + " €");

        }
        //ponemos el resultado del defaullist en nuestro List
        resultadoFiltro.setModel(busquedaClases);

    }

    //Insertar Alumno en Clase\\ //DOne
    public void meterAlumnoClase(JList<String> alumno, JList<String> clase) throws ClassNotFoundException, SQLException {
        //Poner Sentencia a Codigo
        TotalSentencias = "";
                
        //2Jlist recorto por #
        totalAlumno = alumno.getSelectedValue();
        splits = totalAlumno.split("#");
        totalAlumno = splits[0];

        //2Jlist recorto por #
        totalClase = clase.getSelectedValue();
        splits2 = totalClase.split("#");
        totalClase = splits2[0];

        //clases = "SELECT clase FROM clientes WHERE nAlumno =";
        TotalSentencias = clases + totalAlumno;

        rs = select.select(TotalSentencias);
        //Recoremos Rs para sacar que clase tiene el usuario seleccionado
        while (rs.next()) {
            claseQuePosee = rs.getString("clase");
        }
        
        //Como en la bbdd las vacias pone ninguna si es ninguna lo que hay en clases.. lanzara un where alumno igual al que sea y pondra la nueva id de la clase
        if (claseQuePosee.equals("ninguna")){
            TotalSentencias = "";
            //"UPDATE clientes SET clase = ? ";
            TotalSentencias = insertarClaseEnAlumno + "WHERE nAlumno = " + totalAlumno;

            Class.forName(driver);
            con = DriverManager.getConnection(conBBDD, usuarioBBDD, passBBDD);

            prepare = con.prepareStatement(TotalSentencias);
            prepare.setString(1, totalClase);

            prepare.executeUpdate();
            prepare.close();

        }else{ // si ya contiene algo el valor se habra guardo entonces añadriremos el valor primero + el segundo separado por comas
            
            TotalSentencias = "";     
            //"UPDATE clientes SET clase = ? ";
            TotalSentencias = insertarClaseEnAlumno + "WHERE nAlumno = " + totalAlumno;

            Class.forName(driver);
            //Creación de la conexion con la url necesaria
            con = DriverManager.getConnection(conBBDD, usuarioBBDD, passBBDD);
            prepare = con.prepareStatement(TotalSentencias);

            prepare.setString(1, claseQuePosee+","+totalClase);
            prepare.executeUpdate();

            prepare.close();
         }

    }

    //Mostrara la clase de los alumnos marcados // ESTE APARTADO QUEDAN COSAS QUE RETOCAR si TENIA 2 CLASES no las MARCABA \\
    public void mostrarClasesAlumnos(String valorLista,String queryBuscarClaseAlumno,String querySacarDatosClase,JList<String> JListClasesPago){
        
        busquedaClases.clear();
        JListClasesPago.setModel(busquedaClases);
        
        //Pillara la id del alumno cortando por #
        String[] splits = valorLista.split("#");
        valorLista = splits[0];
        //Buscara el alumno con esa id
        queryBuscarClaseAlumno  = queryBuscarClaseAlumno + valorLista;
        
        //Aqui buscara las clases en las que esta ese alumno
       try {
           rs = select.select(queryBuscarClaseAlumno);
      
            while (rs.next()) {
                //Sacara el valor que contenga la clase
                nClase = rs.getString("clase");
            }
            
            //Aqui abria que cortar por ,  para sacar los 2 valores 
            //y hacer un .lenght para ir lanzando sentencias si son varios valores o solo 1
          querySacarDatosClase  =  querySacarDatosClase + nClase;       
          rs1=select.select(querySacarDatosClase);
          
          //Cada valor de la clase del RS se ponmdra en una variable local y se metera en el Defaullist
          while (rs1.next()) {

            ID = rs1.getInt("idClase");
            actividad = rs1.getString("actividad");
            centro = rs1.getString("centro");
            dias = rs1.getString("dias");
            hEntrada = rs1.getString("horaEntrada");
            mEntrada = rs1.getString("minutosEntrada");
            hSalida = rs1.getString("horaSalida");
            mSalida = rs1.getString("minutosSalida");
            precio = rs1.getString("precio");

            
            busquedaClases.addElement(ID + "#" + actividad + " " + centro + " " + dias + " " + hEntrada + ":" + mEntrada + " | " + hSalida + ":" + mSalida + ", " + precio + " €");

            }
           
            //Pondremos el defauilist que tenemos
            JListClasesPago.setModel(busquedaClases);            
            
        }catch (SQLException | ClassNotFoundException ex) {
            
        }
    }
   
    //Insertar un pago / pasamos los 2 Jlist pillamos sus valores y los valores marcados en temporada y precio
    public void insertarPago(JList<String> ClasesPago,JList<String> AlumnoPago,JList<String> mes,JTextField temporada,JTextField precio) throws ClassNotFoundException, SQLException{
        totalAlumno = ClasesPago.getSelectedValue();
        splits = totalAlumno.split("#");
        totalAlumno = splits[0]; //Ya tendria el clase

        totalClase = AlumnoPago.getSelectedValue();
        splits2 = totalClase.split("#");
        totalClase = splits2[0]; //Ya tendria el alumno
        
        //Necesito sacar el Id de profesor de la clase y realizar el insert en pago
        cogerProfesorClase = cogerProfesorClase + totalAlumno;
        rs = select.select(cogerProfesorClase);
        
         while (rs.next()) {
                
            idProfesor = rs.getString("idProfesor");
            
         }
         
        mesPago = mes.getSelectedValue();
        temporadaPago = temporada.getText();
        precioPago = precio.getText();
        
         
        Class.forName(driver);
        con = DriverManager.getConnection(conBBDD, usuarioBBDD, passBBDD);

        // insert 
        prepare = con.prepareStatement(insertarPago);
        prepare.setString(1, temporadaPago);
        prepare.setString(2, mesPago);
        prepare.setString(3, totalAlumno);
        prepare.setString(4, precioPago);
        prepare.setString(5, totalClase);
        prepare.setString(6, idProfesor);

        // Ejecutar
        prepare.execute();

        con.close();
    }
    
}


//////////////////////////////////// ESTA PARTE ES DEL ANTIGUO PROGRAMA MANDABA UN SELECT *  Y PONIA SUS VALORES EN CADA PARTE DEL PROGRAMA ACTUAL PERO ESTABA MAL \\\\\\\\\\\\

//    //Busqueda por Apellidos Enviar Resultado //EL RESULTADO LO DA Falta que lo muestre el controlador\\
//    public void busquedaApellido(String apellido, JTextField txtResultado,
//            ArrayList afiliadoBBDD, ArrayList nombreBBDD, ArrayList apellidoBBDD, ArrayList sexoBBDD, ArrayList ciudadBBDD, ArrayList domicilioBBDD, ArrayList cpBBDD, ArrayList mailBBDD, ArrayList observacionesBBDD,
//            ArrayList nacimientoBBDD, ArrayList movilBBDD, ArrayList fijoBBDD, ArrayList entidadBBDD, ArrayList cuentaBBDD, ArrayList pagoBBDD, ArrayList clienteBBDD) throws ClassNotFoundException, SQLException {
//
//        String queryApellido = "SELECT * FROM `clientes` WHERE `apellido` LIKE '%" + apellido + "%'";
//
//        rs = select.select(queryApellido);
//        //Rellenar toda las opciones
//
//        while (rs.next()) {
//
//            afiliadoBBDD.add(Integer.toString(rs.getInt("nAfiliado")));
//            nombreBBDD.add(rs.getString("nombre"));
//            apellidoBBDD.add(rs.getString("apellido"));
//            sexoBBDD.add(rs.getString("sexo"));
//            ciudadBBDD.add(rs.getString("ciudad"));
//            domicilioBBDD.add(rs.getString("domicilio"));
//            cpBBDD.add(rs.getString("cPostal"));
//            mailBBDD.add(rs.getString("mail"));
//            observacionesBBDD.add(rs.getString("observaciones"));
//            nacimientoBBDD.add(rs.getString("fechaNacimiento"));
//            movilBBDD.add(rs.getString("telefonoMovil"));
//            fijoBBDD.add(rs.getString("telefonoFijo"));
//            entidadBBDD.add(rs.getString("entidad"));
//            cuentaBBDD.add(rs.getString("nCuenta"));
//            pagoBBDD.add(rs.getString("tipoPago"));
//            clienteBBDD.add(Integer.toString(rs.getInt("tipoCliente")));
//
//        }
//
//        txtResultado.setText("" + afiliadoBBDD.size());
//
//    }


//    //Busqueda por Afiliado Enviar Resultado //EL RESULTADO LO DA Falta que lo muestre el controlador\\
//    public void busquedaAfiliado(String afiliado, JTextField txtResultado,
//            ArrayList afiliadoBBDD, ArrayList nombreBBDD, ArrayList apellidoBBDD, ArrayList sexoBBDD, ArrayList ciudadBBDD, ArrayList domicilioBBDD, ArrayList cpBBDD, ArrayList mailBBDD, ArrayList observacionesBBDD,
//            ArrayList nacimientoBBDD, ArrayList movilBBDD, ArrayList fijoBBDD, ArrayList entidadBBDD, ArrayList cuentaBBDD, ArrayList pagoBBDD, ArrayList clienteBBDD) throws ClassNotFoundException, SQLException {
//
//        String querySocio = "SELECT * FROM `clientes` WHERE `nAfiliado` LIKE '%" + afiliado + "%'";
//
//        rs = select.select(querySocio);
//        //Rellenar toda las opciones
//        while (rs.next()) {
//
//            afiliadoBBDD.add(Integer.toString(rs.getInt("nAfiliado")));
//            nombreBBDD.add(rs.getString("nombre"));
//            apellidoBBDD.add(rs.getString("apellido"));
//            sexoBBDD.add(rs.getString("sexo"));
//            ciudadBBDD.add(rs.getString("ciudad"));
//            domicilioBBDD.add(rs.getString("domicilio"));
//            cpBBDD.add(rs.getString("cPostal"));
//            mailBBDD.add(rs.getString("mail"));
//            observacionesBBDD.add(rs.getString("observaciones"));
//            nacimientoBBDD.add(rs.getString("fechaNacimiento"));
//            movilBBDD.add(rs.getString("telefonoMovil"));
//            fijoBBDD.add(rs.getString("telefonoFijo"));
//            entidadBBDD.add(rs.getString("entidad"));
//            cuentaBBDD.add(rs.getString("nCuenta"));
//            pagoBBDD.add(rs.getString("tipoPago"));
//            clienteBBDD.add(Integer.toString(rs.getInt("tipoCliente")));
//
//        }
//
//        txtResultado.setText("" + afiliadoBBDD.size());
//
//    }
//
////El numero de afiliado debe llegar por parametro de la parte de la id que se use ne ca damomento\\
//    public void cargaModificar(String afiliado, ArrayList nAfiliadoActividad, ArrayList actividadActividad, ArrayList centroModificar, ArrayList diasModificar, ArrayList horariosModificar) throws ClassNotFoundException, SQLException {
//
//       // modificarQuery = "Select * FROM 'actividades' WHERE nAfiliado = '" + afiliado + "'";
//        //rs = select.select(modificarQuery);
//
//        while (rs.next()) {
//
//            nAfiliadoActividad.add(Integer.toString(rs.getInt("nAfiliado")));
//            actividadActividad.add(rs.getString("actividad"));
//            centroModificar.add(rs.getString("centro"));
//            diasModificar.add(rs.getString("dias"));
//            horariosModificar.add(rs.getString("horario"));
//
//        }
//
//    }

