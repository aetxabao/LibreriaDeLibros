package com.masanz.ldl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.masanz.ldl.controller.LibreriaController;
import com.masanz.ldl.model.Libro;
import com.masanz.ldl.util.DbUtil;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LibreriaDao {

    private final String PST_INSERT_LIBRO = "INSERT INTO libros VALUES(?, ?, ?, ?, ?)";
    private final String PST_UPDATE_LIBRO = "UPDATE libros SET titulo=?, autor=?, anyo=?, paginas=? WHERE id=?";
    private final String PST_DELETE_LIBRO = "DELETE FROM libros WHERE id=?";
    private final String PST_SELECT_LIBRO = "SELECT id, titulo, autor, anyo, paginas FROM libros WHERE id=?";
    private final String PST_SELECT_LISTA_LIBROS = "SELECT id, titulo, autor, anyo, paginas FROM libros";
    private final String PST_SELECT_LISTA_AUTOR = "SELECT id, titulo, autor, anyo, paginas FROM libros WHERE UPPER(autor) LIKE UPPER(?)";

    private static Logger log = Logger.getLogger(LibreriaController.class);

    public boolean testConnection() {
        return DbUtil.testConnection();
    }

    public void setupConnection(String setup) {
        DbUtil.setDbPath(setup);
    }

    public boolean insertar(Libro libro) {
        try {
            Connection conn = DbUtil.getConnection();
            try(PreparedStatement pst = conn.prepareStatement(PST_INSERT_LIBRO)) {
                pst.setInt(1, libro.getId());
                pst.setString(2, libro.getTitulo());
                pst.setString(3, libro.getAutor());
                pst.setInt(4, libro.getAnyo());
                pst.setInt(5, libro.getPaginas());
                pst.executeUpdate();
            }catch (Exception e) {
                //e.printStackTrace();
                log.debug("Error insertar\n" + e.toString());
                return false;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            log.debug("Error insertar SQLException\n" + e.toString());
            return false;
        }
        return true;
    }

    public boolean actualizar(Libro libro) {
        try {
            Connection conn = DbUtil.getConnection();
            try(PreparedStatement pst = conn.prepareStatement(PST_UPDATE_LIBRO)) {
                pst.setString(1, libro.getTitulo());
                pst.setString(2, libro.getAutor());
                pst.setInt(3, libro.getAnyo());
                pst.setInt(4, libro.getPaginas());
                pst.setInt(5, libro.getId());
                pst.executeUpdate();
            }catch (Exception e) {
                //e.printStackTrace();
                log.debug("Error actualizar\n" + e.toString());
                return false;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            log.debug("Error actualizar SQLException\n" + e.toString());
            return false;
        }
        return true;
    }

    public boolean borrar(int id) {
        try {
            Connection conn = DbUtil.getConnection();
            try(PreparedStatement pst = conn.prepareStatement(PST_DELETE_LIBRO)) {
                pst.setInt(1, id);
                pst.executeUpdate();
            }catch (Exception e) {
                //e.printStackTrace();
                log.debug("Error borrar\n" + e.toString());
                return false;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            log.debug("Error borrar SQLException\n" + e.toString());
            return false;
        }
        return true;
    }

    public Libro libro(int id) {
        try {
            Connection conn = DbUtil.getConnection();
            try(PreparedStatement pst = conn.prepareStatement(PST_SELECT_LIBRO)) {
                pst.setInt(1, id);
                try (ResultSet rs = pst.executeQuery()){
                    if (rs.next()){
                        return new Libro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            log.debug("Error libro SQLException\n" + e.toString());
            return null;
        }
    }

    public List<Libro> listaLibros() {
        List<Libro> lista = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            try(PreparedStatement pst = conn.prepareStatement(PST_SELECT_LISTA_LIBROS)) {
                try (ResultSet rs = pst.executeQuery()){
                    while (rs.next()){
                        Libro libro = new Libro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
                        lista.add(libro);
                    }
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            log.debug("Error listaLibros SQLException\n" + e.toString());
            return null;
        }
        return lista;
    }

    public List<Libro> listaLibrosAutor(String autor) {
        List<Libro> lista = new ArrayList<>();
        try {
            Connection conn = DbUtil.getConnection();
            try(PreparedStatement pst = conn.prepareStatement(PST_SELECT_LISTA_AUTOR)) {
                pst.setString(1, "%" + autor + "%");
                try (ResultSet rs = pst.executeQuery()){
                    while (rs.next()){
                        Libro libro = new Libro(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
                        lista.add(libro);
                    }
                }
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            log.debug("Error listaLibros autor SQLException\n" + e.toString());
            return null;
        }
        return lista;
    }

}
