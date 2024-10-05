package view;

import controllers.ClienteController;
import models.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class ClienteGUI extends JFrame {
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField telefoneField;
    private JTable listArea;
    private DefaultTableModel model;
    private Cliente cliente;

    public ClienteGUI() {
        setTitle("Gerenciamento de Clientes");
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
        JLabel nomeLabel = new JLabel("Nome:");
        inputPanel.add(nomeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        nomeField = new JTextField(20);
        inputPanel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel cpfLabel = new JLabel("CPF:");
        inputPanel.add(cpfLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        cpfField = new JTextField(20);
        inputPanel.add(cpfField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel telefoneLabel = new JLabel("Telefone:");
        inputPanel.add(telefoneLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        telefoneField = new JTextField(20);
        inputPanel.add(telefoneField, gbc);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));
        JButton cadastrarButton = new JButton("Cadastrar");
        JButton atualizarButton = new JButton("Atualizar");
        JButton removerButton = new JButton("Remover");

        buttonPanel.add(cadastrarButton);
        buttonPanel.add(atualizarButton);
        buttonPanel.add(removerButton);


        actionPanel.add(inputPanel, BorderLayout.CENTER);
        actionPanel.add(buttonPanel, BorderLayout.SOUTH);


        String[] colunas = {"ID","Nome","CPF","Telefone"};
        model = new DefaultTableModel(colunas, 0);

        listArea = new JTable(model);
        listArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = listArea.rowAtPoint(e.getPoint());

                if (row != -1) {
                    Object id = listArea.getValueAt(row,0);
                    Object nome = listArea.getValueAt(row,1);
                    Object CPF = listArea.getValueAt(row,2);
                    Object Telefone = listArea.getValueAt(row,3);
                    try {
                        cliente = Cliente.Find(Integer.valueOf(id.toString()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    nomeField.setText(nome.toString());
                    cpfField.setText(CPF.toString());
                    telefoneField.setText(Telefone.toString());
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(listArea);

        add(menuPanel, BorderLayout.WEST);
        add(actionPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);


        fornecedorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FornecedorGUI();
                dispose();
            }
        });

        vendasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VendaGUI();
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


        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarCliente();
            }
        });

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerCliente();
            }
        });

        setVisible(true);

        listarClientes();
        cliente = null;
    }

    private void cadastrarCliente() {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String telefone = telefoneField.getText();
        ClienteController.SaveClient(nome, cpf, telefone);
        JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
        clearFields();
        listarClientes();
    }

    private void listarClientes() {
        model.setRowCount(0);
        String[][] data = ClienteController.ClientGetDataList();
        int i = 0;
        for(Object[] d : data) {
            model.insertRow(i++,d);
        }
    }

    private void atualizarCliente() {
        if(cliente != null) {
            String nome = nomeField.getText();
            String cpf = cpfField.getText();
            String telefone = telefoneField.getText();
            ClienteController.UpdateClient(cliente.getId(), nome, cpf, telefone);
            clearFields();
            listarClientes();
            cliente = null;
        }
    }

    private void removerCliente() {
        if(cliente != null) {
            try {
                cliente.Delete();
                cliente = null;
                clearFields();
                listarClientes();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void clearFields() {
        nomeField.setText("");
        cpfField.setText("");
        telefoneField.setText("");
    }

    public static void main(String[] args) {
        new ClienteGUI();
    }
}
