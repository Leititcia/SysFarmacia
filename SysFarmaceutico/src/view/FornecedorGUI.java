package view;

import controllers.ClienteController;
import controllers.FornecedorController;
import models.Cliente;
import models.Fornecedor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class FornecedorGUI extends JFrame {
    private JTextField nomeField;
    private JTextField cnpjField;
    private JTextField telefoneField;
    private JTable listArea;
    private DefaultTableModel model;
    private Fornecedor fornecedor;

    public FornecedorGUI() {
        setTitle("Gerenciamento de Fornecedores");
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
        JLabel cnpjLabel = new JLabel("CNPJ:");
        inputPanel.add(cnpjLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        cnpjField = new JTextField(20);
        inputPanel.add(cnpjField, gbc);

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

        String[] colunas = {"ID","Nome","CNPJ","Telefone"};
        model = new DefaultTableModel(colunas, 0);
        listArea = new JTable(model);
        listArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = listArea.rowAtPoint(e.getPoint());

                if (row != -1) {
                    Object id = listArea.getValueAt(row,0);
                    Object nome = listArea.getValueAt(row,1);
                    Object cnpj = listArea.getValueAt(row,2);
                    Object Telefone = listArea.getValueAt(row,3);
                    try {
                        fornecedor = fornecedor.Find(Integer.valueOf(id.toString()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    nomeField.setText(nome.toString());
                    cnpjField.setText(cnpj.toString());
                    telefoneField.setText(Telefone.toString());
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(listArea);

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
                cadastrarFornecedor();
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarFornecedor();
            }
        });

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerFornecedor();
            }
        });

        setVisible(true);
        listarFornecedores();
    }

    private void cadastrarFornecedor() {
        String nome = nomeField.getText();
        String cnpj = cnpjField.getText();
        String telefone = telefoneField.getText();
        FornecedorController.SaveFornecedor(nome, cnpj, telefone);
        JOptionPane.showMessageDialog(this, "Fornecedor cadastrado com sucesso!");
        clearFields();
        listarFornecedores();
    }

    private void listarFornecedores() {
        model.setRowCount(0);
        String[][] data = FornecedorController.FornecedorGetDataList();
        int i = 0;
        for(Object[] d : data) {
            model.insertRow(i++,d);
        }
    }

    private void atualizarFornecedor() {
        if(fornecedor != null) {
            String nome = nomeField.getText();
            String cnpj = cnpjField.getText();
            String telefone = telefoneField.getText();
            FornecedorController.UpdateFornecedor(fornecedor.getId(), nome, cnpj, telefone);
            clearFields();
            listarFornecedores();
            fornecedor = null;
        }
    }

    private void removerFornecedor() {
        if(fornecedor != null) {
            try {
                fornecedor.Delete();
                fornecedor = null;
                clearFields();
                listarFornecedores();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void clearFields() {
        nomeField.setText("");
        cnpjField.setText("");
        telefoneField.setText("");
    }

    public static void main(String[] args) {
        new FornecedorGUI();
    }
}
