package view;

import controllers.MedicamentoController;
import controllers.VendaController;
import models.Fornecedor;
import models.Venda;
import models.Cliente;
import models.Medicamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class VendaGUI extends JFrame {
    private JComboBox<String> clienteComboBox;
    private JComboBox<String> medicamentoComboBox;
    private JTextField quantidadeField;
    private JTextField precoField;
    private JTextField dataField;
    private JTable listTable;
    private DefaultTableModel model;

    private Cliente cliente;
    private Medicamento medicamento;
    private Venda venda;

    private ArrayList<Cliente> clientes;
    private ArrayList<Medicamento> medicamentos;

    public VendaGUI() {
        setTitle("Gerenciamento de Vendas");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1));
        JButton clienteButton = new JButton("Cliente");
        JButton fornecedorButton = new JButton("Fornecedor");
        JButton medicamentosButton = new JButton("Medicamentos");
        JButton vendasButton = new JButton("Vendas");

        menuPanel.add(clienteButton);
        menuPanel.add(fornecedorButton);
        menuPanel.add(medicamentosButton);
        menuPanel.add(vendasButton);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel clienteLabel = new JLabel("Cliente:");
        inputPanel.add(clienteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        clienteComboBox = new JComboBox<>(getClientes());
        inputPanel.add(clienteComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel medicamentoLabel = new JLabel("Medicamento:");
        inputPanel.add(medicamentoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        medicamentoComboBox = new JComboBox<>(getMedicamentos());
        inputPanel.add(medicamentoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel quantidadeLabel = new JLabel("Quantidade:");
        inputPanel.add(quantidadeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        quantidadeField = new JTextField(20);
        inputPanel.add(quantidadeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel dataLabel = new JLabel("Data (dd/MM/yyyy):");
        inputPanel.add(dataLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        dataField = new JTextField(20);
        inputPanel.add(dataField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));
        JButton vendaButton = new JButton("Realizar Venda");

        buttonPanel.add(vendaButton);

        actionPanel.add(inputPanel, BorderLayout.CENTER);
        actionPanel.add(buttonPanel, BorderLayout.SOUTH);


        String[] colunas = {"ID", "Cliente", "Medicamento", "Quantidade", "Preço", "Data"};
        model = new DefaultTableModel(colunas, 0);
        listTable = new JTable(model);
        listTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = listTable.rowAtPoint(e.getPoint());

                if (row != -1) {
                    Object id = listTable.getValueAt(row,0);
                    Object cliente = listTable.getValueAt(row,1);
                    Object medicamento = listTable.getValueAt(row,2);
                    Object quantidade = listTable.getValueAt(row,3);


                    quantidadeField.setText(quantidade.toString());
                    try {
                        venda = Venda.Find(Integer.parseInt(id.toString()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    for(int i = 0; i < clienteComboBox.getItemCount(); i++){
                        String s = clienteComboBox.getItemAt(i);
                        if(Objects.equals(s, cliente.toString())){
                            clienteComboBox.setSelectedIndex(i);
                        }
                    }
                    for(int i = 0; i < medicamentoComboBox.getItemCount(); i++){
                        String s = medicamentoComboBox.getItemAt(i);
                        if(Objects.equals(s, medicamento.toString())){
                            medicamentoComboBox.setSelectedIndex(i);
                        }
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(listTable);

        add(menuPanel, BorderLayout.WEST);
        add(actionPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);


        clienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClienteGUI();
                dispose();
            }
        });

        fornecedorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FornecedorGUI();
                dispose();
            }
        });

        medicamentosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MedicamentoGUI();
                dispose();
            }
        });

    //CRUD
        vendaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarVenda();
            }
        });

        setVisible(true);
        listarVendas();
    }

    private String[] getClientes() {
        try {
            if(clientes == null){
                clientes = Cliente.AllClientes();
            }

            String[] l = new String[clientes.size()];
            for (int i = 0; i < clientes.size(); i++){
                Cliente cliente = clientes.get(i);
                l[i] = String.format("%s (%s)",cliente.getNome(),cliente.getCpf());
            }
            return l;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String[] getMedicamentos() {
        try {
            if(medicamentos == null){
                medicamentos = Medicamento.All();
            }

            String[] l = new String[medicamentos.size()];
            for (int i = 0; i < medicamentos.size(); i++){
                Medicamento medicamento = medicamentos.get(i);
                l[i] = String.format("%s (%.2f)",medicamento.getNome(),medicamento.getPreco());
            }
            return l;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void cadastrarVenda() {
        //String nome = nomeField.getText();
        //String data = dataField.getText();
        String quantidade = quantidadeField.getText();
        Cliente cliente = clientes.get(clienteComboBox.getSelectedIndex());
        Medicamento medicamento = medicamentos.get(medicamentoComboBox.getSelectedIndex());
        VendaController.SaveVenda(cliente, medicamento, quantidade);
        JOptionPane.showMessageDialog(this, "Medicamento cadastrado com sucesso!");
        clearFields();
        listarVendas();
    }

    private void listarVendas() {
        model.setRowCount(0);
        String[][] data = VendaController.VendasGetDataList();
        int i = 0;
        for(Object[] d : data) {
            model.insertRow(i++,d);
        }
    }

    private void atualizarVenda() {
        if(venda != null) {
            String quantidade = quantidadeField.getText();
            Cliente cliente = clientes.get(clienteComboBox.getSelectedIndex());
            Medicamento medicamento = medicamentos.get(medicamentoComboBox.getSelectedIndex());
            VendaController.UpdateVenda(venda.getId(), cliente, medicamento, quantidade);
            clearFields();
            listarVendas();
            venda = null;
        }
    }

    private void removerVenda() {
        if(venda != null) {
            try {
                venda.Delete();
                venda = null;
                clearFields();
                listarVendas();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void clearFields() {
        clienteComboBox.setSelectedIndex(0); // Reseta a ComboBox de cliente
        medicamentoComboBox.setSelectedIndex(0); // Reseta a ComboBox de medicamento
        quantidadeField.setText("");
        dataField.setText("");
    }

    public static void main(String[] args) {
        new VendaGUI();
    }
}
