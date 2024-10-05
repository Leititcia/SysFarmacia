package models;

import db.ConexaoBancoDeDados;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venda {
    private int id;
    private Cliente cliente;
    private Medicamento medicamento;
    private int quantidade;
    private Date data;
    private double precoTotal;

    private static List<Venda> vendas = new ArrayList<>();

    public Venda(int id, Cliente cliente, Medicamento medicamento, int quantidade, Date data) {
        this.id = id;
        this.cliente = cliente;
        this.medicamento = medicamento;
        this.quantidade = quantidade;
        this.data = data;
        this.precoTotal = medicamento.getPreco() * quantidade;
        vendas.add(this);
    }

    public Venda( Cliente cliente, Medicamento medicamento, int quantidade, Date data) {
        this.id = -1;
        this.cliente = cliente;
        this.medicamento = medicamento;
        this.quantidade = quantidade;
        this.data = data;
        this.precoTotal = medicamento.getPreco() * quantidade;
        vendas.add(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) { // Corrigido para void
        this.medicamento = medicamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) { // Corrigido para void
        this.quantidade = quantidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    protected void CreateData() throws SQLException {
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            String sql = "INSERT INTO venda (cliente_id, medicamento_id, quantidade, preco_total) VALUE ( ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, cliente.getId());
            preparedStatement.setDouble(2, medicamento.getId());
            preparedStatement.setInt(3, quantidade);
            preparedStatement.setDouble(4, quantidade*medicamento.getPreco());

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
            String sql = "UPDATE venda SET cliente_id = ?, medicamento_id = ?, quantidade = ?, preco_total = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cliente.getId());
            preparedStatement.setDouble(2, medicamento.getId());
            preparedStatement.setInt(3, quantidade);
            preparedStatement.setDouble(4, quantidade*medicamento.getPreco());
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
                String sql = "DELETE FROM venda WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();
                this.id = -1;
            }
        }
    }

    public String toString(){
        return "";
        //return String.format("Venda{id: %d, nome: %s, preco: %f, quantidade: %d, fornecedor: %s}", id, nome, preco, quantidade, fornecedor.toString());
    }

    public static Venda Find(int id) throws SQLException {
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM venda WHERE id = " + id;

            ResultSet resultSet = statement.executeQuery(sql);

            Venda res = null;

            if(resultSet.next()){
                Cliente cliente  = Cliente.Find(resultSet.getInt("cliente_id"));
                Medicamento medicamento  = Medicamento.Find(resultSet.getInt("medicamento_id"));
                res = new Venda(
                        id,
                        cliente,
                        medicamento,
                        resultSet.getInt("quantidade"),
                        resultSet.getDate("data")
                );
            }

            return res;
        }
        return null;
    }

    public static ArrayList<Venda> All() throws SQLException {
        Connection connection = ConexaoBancoDeDados.getConnection();
        if(connection != null){
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM venda";

            ResultSet resultSet = statement.executeQuery(sql);

            ArrayList<Venda> res = new ArrayList<>();

            while (resultSet.next()){
                Cliente cliente  = Cliente.Find(resultSet.getInt("cliente_id"));
                Medicamento medicamento  = Medicamento.Find(resultSet.getInt("medicamento_id"));
                res.add(new Venda(
                        resultSet.getInt("id"),
                        cliente,
                        medicamento,
                        resultSet.getInt("quantidade"),
                        resultSet.getDate("data")
                ));
            }

            return res;
        }
        return null;
    }
}
