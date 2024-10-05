package models;

import db.ConexaoBancoDeDados;

import java.sql.*;
import java.util.ArrayList;

public class Cliente extends Pessoa {
    private String cpf;


    public Cliente(int id, String nome, String telefone, String cpf) {
        super(id, nome, telefone);
        this.cpf = cpf;
    }

    public Cliente(String nome, String telefone, String cpf) {
       super(nome, telefone);
       this.cpf = cpf;
    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    protected void CreateData() throws SQLException {
        super.CreateData();
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            String sql = "INSERT INTO cliente (id, cpf) VALUE (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, cpf);

            preparedStatement.executeUpdate();
        }
    }

    protected void UpdateData() throws SQLException {
        super.UpdateData();
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            String sql = "UPDATE cliente SET cpf = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cpf);
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();
        }
    }

    public static Cliente Find(int id) throws SQLException {
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM cliente JOIN pessoa on pessoa.id = cliente.id WHERE cliente.id = " + id;

            ResultSet resultSet = statement.executeQuery(sql);

            Cliente res = null;

            if(resultSet.next()){
                res = new Cliente(
                        id,
                        resultSet.getString("nome"),
                        resultSet.getString("telefone"),
                        resultSet.getString("cpf")
                );
            }

            return res;
        }
        return null;
    }

    public static ArrayList<Cliente> AllClientes() throws SQLException {
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM cliente JOIN pessoa on pessoa.id = cliente.id";

            ResultSet resultSet = statement.executeQuery(sql);

            ArrayList<Cliente> res = new ArrayList<>();

            while (resultSet.next()){
                res.add(new Cliente(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("telefone"),
                        resultSet.getString("cpf")
                ));
            }

            return res;
        }
        return null;
    }

    public String toString(){
        return String.format("Cliente{id: %d, nome: %s, telefone: %s, cpf: %s}", id, getNome(), getId(), cpf);
    }
}
