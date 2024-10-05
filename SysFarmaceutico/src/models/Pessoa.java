package models;

import db.ConexaoBancoDeDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Pessoa {
    protected int id;
    private String nome;
    private String telefone;

    public Pessoa(int id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public Pessoa(String nome, String telefone) {
        this.id = -1;
        this.nome = nome;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    protected void CreateData() throws SQLException {
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            String sql = "INSERT INTO pessoa (nome, telefone) VALUE (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, telefone);

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0){
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.id = generatedKeys.getInt(1);
                    }
                }
            }
        }
    }

    protected void UpdateData() throws SQLException {
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            String sql = "UPDATE pessoa SET nome = ?, telefone = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, telefone);
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();
        }
    }

    public void Save() throws SQLException {
        if(id == -1){
            this.CreateData();
        } else {
            this.UpdateData();
        }
    }

    public void Delete() throws SQLException {
        if(id != -1){
            Connection connection = ConexaoBancoDeDados.getConnection();
            if(connection != null){
                String sql = "DELETE FROM pessoa WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();
                this.id = -1;
            }
        }
    }

    public String toString(){
        return String.format("Pessoa{id: %d, nome: %s, telefone: %s}", id, nome, telefone);
    }

    public static Pessoa Find(int id) throws SQLException {
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM pessoa WHERE id = " + id;

            ResultSet resultSet = statement.executeQuery(sql);

            Pessoa res = null;

            if(resultSet.next()){
                res = new Pessoa(id, resultSet.getString("nome"), resultSet.getString("telefone"));
            }

            return res;
        }
        return null;
    }

    public static ArrayList<Pessoa> All() throws SQLException {
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM pessoa";

            ResultSet resultSet = statement.executeQuery(sql);

            ArrayList<Pessoa> res = new ArrayList<>();

            while (resultSet.next()){
                res.add(new Pessoa(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("telefone")));
            }

            return res;
        }
        return null;
    }
}
