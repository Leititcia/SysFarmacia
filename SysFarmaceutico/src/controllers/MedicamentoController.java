package controllers;

import models.Fornecedor;
import models.Medicamento;

import java.sql.SQLException;
import java.util.ArrayList;

public class MedicamentoController {
    public static String[][] MedicamentosGetDataList(){
        try {
            ArrayList<Medicamento> medicamentos = Medicamento.All();
            String[][] data = new String[medicamentos.size()][5];

            for (int i = 0; i < medicamentos.size(); i++) {
                Medicamento medicamento = medicamentos.get(i);
                data[i][0] = String.valueOf(medicamento.getId());
                data[i][1] = medicamento.getNome();
                data[i][2] = String.valueOf(medicamento.getPreco());
                data[i][3] = String.valueOf(medicamento.getQuantidade());
                Fornecedor f = medicamento.getFornecedor();
                data[i][4] = String.format("%s (%s)",f.getNome(), f.getCnpj());
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void SaveMedicamento(String nome, String preco, String quantidade, Fornecedor fornecedor){
        Medicamento medicamento = new Medicamento(nome, Double.parseDouble(preco), Integer.parseInt(quantidade), fornecedor);
        try {
            medicamento.Save();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void UpdateMedicamento(int id, String nome, String preco, String quantidade, Fornecedor fornecedor){
        Medicamento medicamento = null;
        try {
            medicamento = Medicamento.Find(id);
            medicamento.setNome(nome);
            medicamento.setPreco(Double.parseDouble(preco));
            medicamento.setQuantidade(Integer.parseInt(quantidade));
            medicamento.setFornecedor(fornecedor);
            medicamento.Save();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
