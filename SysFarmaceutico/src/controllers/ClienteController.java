package controllers;

import models.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteController {
    public static String[][] ClientGetDataList(){
        try {
            ArrayList<Cliente> clientes = Cliente.AllClientes();
            String[][] data = new String[clientes.size()][4];

            for (int i = 0; i < clientes.size(); i++) {
                Cliente cliente = clientes.get(i);
                data[i][0] = String.valueOf(cliente.getId());
                data[i][1] = cliente.getNome();
                data[i][2] = cliente.getCpf();
                data[i][3] = cliente.getTelefone();
            }
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void SaveClient(String nome, String cpf, String telefone){
        Cliente cliente = new Cliente(nome, telefone, cpf);
        try {
            cliente.Save();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void UpdateClient(int id, String nome, String cpf, String telefone){
        Cliente cliente = null;
        try {
            cliente = Cliente.Find(id);
            cliente.setNome(nome);
            cliente.setCpf(cpf);
            cliente.setTelefone(telefone);
            cliente.Save();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
