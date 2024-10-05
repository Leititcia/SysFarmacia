package controllers;

import models.Cliente;
import models.Fornecedor;

import java.sql.SQLException;
import java.util.ArrayList;

public class FornecedorController {
    public static String[][] FornecedorGetDataList(){
        try {
            ArrayList<Fornecedor> fornecedores = Fornecedor.AllFornecedor();
            String[][] data = new String[fornecedores.size()][4];

            for (int i = 0; i < fornecedores.size(); i++) {
                Fornecedor fornecedor = fornecedores.get(i);
                data[i][0] = String.valueOf(fornecedor.getId());
                data[i][1] = fornecedor.getNome();
                data[i][2] = fornecedor.getCnpj();
                data[i][3] = fornecedor.getTelefone();
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void SaveFornecedor(String nome, String cnpj, String telefone){
        Fornecedor fornecedor = new Fornecedor(nome, cnpj, telefone);
        try {
            fornecedor.Save();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void UpdateFornecedor(int id, String nome, String cnpj, String telefone){
        Fornecedor fornecedor = null;
        try {
            fornecedor = Fornecedor.Find(id);
            fornecedor.setNome(nome);
            fornecedor.setCnpj(cnpj);
            fornecedor.setTelefone(telefone);
            fornecedor.Save();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
