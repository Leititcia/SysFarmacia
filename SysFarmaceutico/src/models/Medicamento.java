package models;

import db.ConexaoBancoDeDados;

import java.sql.*;
import java.util.ArrayList;

public class Medicamento {
    private int id;
    private String nome;
    private Double preco;
    private int quantidade;
    private Fornecedor fornecedor;


    public Medicamento(int id, String nome, double preco, int quantidade, Fornecedor fornecedor) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.fornecedor = fornecedor;
    }

    public Medicamento(String nome, double preco, int quantidade, Fornecedor fornecedor) {
        this.nome = nome;
        this.id = -1; // Você pode querer mudar a lógica para o id
        this.preco = preco; // Converte string para double
        this.quantidade = quantidade;
        this.fornecedor = fornecedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    protected void CreateData() throws SQLException {
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            String sql = "INSERT INTO medicamento (nome, preco, quantidade, fornecedor_id) VALUE (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nome);
            preparedStatement.setDouble(2, preco);
            preparedStatement.setInt(3, quantidade);
            preparedStatement.setInt(4, fornecedor.getId());

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
            String sql = "UPDATE medicamento SET nome = ?, preco = ?, quantidade = ?, fornecedor_id = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            preparedStatement.setDouble(2, preco);
            preparedStatement.setInt(3, quantidade);
            preparedStatement.setInt(4, fornecedor.getId());
            preparedStatement.setInt(5, id);

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
                String sql = "DELETE FROM medicamento WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();
                this.id = -1;
            }
        }
    }

    public String toString(){
        return String.format("Medicamento{id: %d, nome: %s, preco: %f, quantidade: %d, fornecedor: %s}", id, nome, preco, quantidade, fornecedor.toString());
    }

    public static Medicamento Find(int id) throws SQLException {
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM medicamento WHERE id = " + id;

            ResultSet resultSet = statement.executeQuery(sql);

            Medicamento res = null;

            if(resultSet.next()){
                Fornecedor f = Fornecedor.Find(resultSet.getInt("fornecedor_id"));
                res = new Medicamento(
                        id,
                        resultSet.getString("nome"),
                        resultSet.getDouble("preco"),
                        resultSet.getInt("quantidade"),
                        f
                );
            }

            return res;
        }
        return null;
    }

    public static ArrayList<Medicamento> All() throws SQLException {
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM medicamento";

            ResultSet resultSet = statement.executeQuery(sql);

            ArrayList<Medicamento> res = new ArrayList<>();

            while (resultSet.next()){
                Fornecedor f = Fornecedor.Find(resultSet.getInt("fornecedor_id"));
                res.add(new Medicamento(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getDouble("preco"),
                        resultSet.getInt("quantidade"),
                        f
                ));
            }

            return res;
        }
        return null;
    }

}
