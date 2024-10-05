package controllers;

import models.Cliente;
import models.Fornecedor;
import models.Medicamento;
import models.Venda;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class VendaController {
    public static String[][] VendasGetDataList(){
        try {
            ArrayList<Venda> vendas = Venda.All();
            String[][] data = new String[vendas.size()][6];

            for (int i = 0; i < vendas.size(); i++) {
                Venda venda = vendas.get(i);
                Cliente cliente = venda.getCliente();
                Medicamento medicamento = venda.getMedicamento();
                data[i][0] = String.valueOf(venda.getId());
                data[i][1] = String.format("%s (%s)",cliente.getNome(), cliente.getCpf());
                data[i][2] = String.format("%s (%.2f)",medicamento.getNome(), medicamento.getPreco());
                data[i][3] = String.valueOf(venda.getQuantidade());
                data[i][4] = String.valueOf(venda.getPrecoTotal());
                data[i][5] = String.valueOf(venda.getData());
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void SaveVenda(Cliente cliente, Medicamento medicamento, String quantidade){
        Venda venda = new Venda(
                cliente, medicamento, Integer.parseInt(quantidade), new Date(System.currentTimeMillis())
        );
        try {
            venda.Save();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void UpdateVenda(int id, Cliente cliente, Medicamento medicamento, String quantidade){
        Venda venda = null;
        try {
            venda = Venda.Find(id);
            venda.setCliente(cliente);
            venda.setMedicamento(medicamento);
            venda.setQuantidade(Integer.parseInt(quantidade));
            venda.setData(new Date(System.currentTimeMillis()));
            venda.Save();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
