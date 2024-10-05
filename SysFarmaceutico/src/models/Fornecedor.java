package models;

import db.ConexaoBancoDeDados;

import java.sql.*;
import java.util.ArrayList;

public class Fornecedor extends Pessoa {
    private String cnpj;


    public Fornecedor(int id, String nome, String telefone, String cnpj) {
        super(id, nome, telefone);
        this.cnpj = cnpj;
    }

    public Fornecedor(String nome, String cnpj, String telefone) {
        super(nome, telefone);
        this.cnpj = cnpj;
    }


    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String getNome() {
        return super.getNome(); // Retorna o nome da classe pai
    }

    protected void CreateData() throws SQLException {
        super.CreateData();
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            String sql = "INSERT INTO fornecedor (id, cnpj) VALUE (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, cnpj);

            preparedStatement.executeUpdate();
        }
    }

    protected void UpdateData() throws SQLException {
        super.UpdateData();
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            String sql = "UPDATE fornecedor SET cnpj = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cnpj);
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();
        }
    }

    public static Fornecedor Find(int id) throws SQLException {
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM fornecedor JOIN pessoa on pessoa.id = fornecedor.id WHERE fornecedor.id = " + id;

            ResultSet resultSet = statement.executeQuery(sql);

            Fornecedor res = null;

            if(resultSet.next()){
                res = new Fornecedor(
                        id,
                        resultSet.getString("nome"),
                        resultSet.getString("telefone"),
                        resultSet.getString("cnpj")
                );
            }

            return res;
        }
        return null;
    }

    public static ArrayList<Fornecedor> AllFornecedor() throws SQLException {
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM fornecedor JOIN pessoa on pessoa.id = fornecedor.id";

            ResultSet resultSet = statement.executeQuery(sql);

            ArrayList<Fornecedor> res = new ArrayList<>();

            while (resultSet.next()){
                res.add(new Fornecedor(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("telefone"),
                        resultSet.getString("cnpj")
                ));
            }

            return res;
        }
        return null;
    }

    public String toString(){
        return String.format("Fornecedor{id: %d, nome: %s, telefone: %s, cnpj: %s}", id, getNome(), getId(), cnpj);
    }
}
