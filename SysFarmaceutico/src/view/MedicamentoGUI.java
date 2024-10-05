package view;

import controllers.FornecedorController;
import controllers.MedicamentoController;
import models.Fornecedor;
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

public class MedicamentoGUI extends JFrame {
    private JTextField nomeField;
    private JTextField precoField;
    private JTextField quantidadeField;
    private JTable medicamentoTable; // Adicionando JTable

    private DefaultTableModel tableModel; // Modelo da tabela
    private JComboBox<String> fornecedorComboBox;

    private ArrayList<Fornecedor> fornecedores; // Lista de fornecedores
    private Medicamento medicamento;

    public MedicamentoGUI() {
        setTitle("Gerenciamento de Medicamentos");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Inicializa a lista de fornecedores e medicamentos
        try {
            fornecedores = Fornecedor.AllFornecedor();
        } catch (SQLException e) {
            fornecedores = new ArrayList<>();
            throw new RuntimeException(e);
        }

        medicamento = null;

        // Painel lateral para navegação
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

        // Painel de entrada e botões
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Campos de entrada
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nomeLabel = new JLabel("Medicamento:");
        inputPanel.add(nomeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        nomeField = new JTextField(20);
        inputPanel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1; // Atualizei o índice de linha
        JLabel precoLabel = new JLabel("Preço:");
        inputPanel.add(precoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        precoField = new JTextField(20);
        inputPanel.add(precoField, gbc);

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
        JLabel fornecedorLabel = new JLabel("Fornecedor:");
        inputPanel.add(fornecedorLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        fornecedorComboBox = new JComboBox<String>(); // Inicializa o JComboBox vazio
        inputPanel.add(fornecedorComboBox, gbc);

        // Botões de ação
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3)); // Atualizando para 3 botões
        JButton cadastrarButton = new JButton("Cadastrar");
        JButton atualizarButton = new JButton("Atualizar");
        JButton removerButton = new JButton("Remover");

        buttonPanel.add(cadastrarButton);
        buttonPanel.add(atualizarButton);
        buttonPanel.add(removerButton);

        actionPanel.add(inputPanel, BorderLayout.CENTER);
        actionPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Tabela para listar medicamentos
        String[] columnNames = {"ID", "Medicamento", "Preço", "Quantidade", "Fornecedor"};
        tableModel = new DefaultTableModel(columnNames, 0); // Modelo da tabela
        medicamentoTable = new JTable(tableModel);
        medicamentoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = medicamentoTable.rowAtPoint(e.getPoint());

                if (row != -1) {
                    Object id = medicamentoTable.getValueAt(row,0);
                    Object nome = medicamentoTable.getValueAt(row,1);
                    Object preco = medicamentoTable.getValueAt(row,2);
                    Object quantidade = medicamentoTable.getValueAt(row,3);
                    Object fornecedor = medicamentoTable.getValueAt(row,4);
                    try {
                        medicamento = Medicamento.Find(Integer.valueOf(id.toString()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    nomeField.setText(nome.toString());
                    precoField.setText(preco.toString());
                    quantidadeField.setText(quantidade.toString());
                    for(int i = 0; i < fornecedorComboBox.getItemCount(); i++){
                        String s = fornecedorComboBox.getItemAt(i);
                        if(Objects.equals(s, fornecedor.toString())){
                            fornecedorComboBox.setSelectedIndex(i);
                        }
                    }
                }
            }
        });
        JScrollPane tableScrollPane = new JScrollPane(medicamentoTable); // Scroll para a tabela

        // Adiciona os componentes ao JFrame
        add(menuPanel, BorderLayout.WEST);
        add(actionPanel, BorderLayout.CENTER);
        add(tableScrollPane, BorderLayout.SOUTH);

        // Carregar fornecedores após a criação do JComboBox
        carregarFornecedores();

        // Ações de navegação
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

        vendasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VendaGUI();
                dispose();
            }
        });

        // Ações CRUD
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarMedicamento();
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarMedicamento();
            }
        });

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerMedicamento();
            }
        });

        setVisible(true);
        listarMedicamentos();
    }

    private void carregarFornecedores() {
        // Aqui você deve carregar os fornecedores de uma fonte de dados, como um banco de dados ou uma lista pré-definida
        // Para fins de demonstração, vamos adicionar alguns fornecedores de exemplo

        // Atualiza o JComboBox com os fornecedores
        fornecedorComboBox.removeAllItems(); // Limpa itens existentes
        for (Fornecedor fornecedor : fornecedores) {
            fornecedorComboBox.addItem(String.format("%s (%s)",fornecedor.getNome(),fornecedor.getCnpj())); // Adiciona cada fornecedor ao JComboBox
        }
    }

    private void cadastrarMedicamento() {
        String nome = nomeField.getText();
        String preco = precoField.getText();
        String quantidade = quantidadeField.getText();
        Fornecedor fornecedor = fornecedores.get(fornecedorComboBox.getSelectedIndex());
        MedicamentoController.SaveMedicamento(nome, preco, quantidade, fornecedor);
        JOptionPane.showMessageDialog(this, "Medicamento cadastrado com sucesso!");
        clearFields();
        listarMedicamentos();
    }

    private void listarMedicamentos() {
        tableModel.setRowCount(0);
        String[][] data = MedicamentoController.MedicamentosGetDataList();
        int i = 0;
        for(Object[] d : data) {
            tableModel.insertRow(i++,d);
        }
    }


    private void atualizarMedicamento() {
        if(medicamento != null) {
            String nome = nomeField.getText();
            String preco = precoField.getText();
            String quantidade = quantidadeField.getText();
            Fornecedor fornecedor = fornecedores.get(fornecedorComboBox.getSelectedIndex());
            MedicamentoController.UpdateMedicamento(medicamento.getId(), nome, preco, quantidade, fornecedor);
            clearFields();
            listarMedicamentos();
            medicamento = null;
        }
    }

    private void removerMedicamento() {
        if(medicamento != null) {
            try {
                medicamento.Delete();
                medicamento = null;
                clearFields();
                listarMedicamentos();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void clearFields() {
        nomeField.setText("");
        precoField.setText("");
        quantidadeField.setText("");
        fornecedorComboBox.setSelectedIndex(0); // Reseta a seleção do JComboBox
    }

    public static void main(String[] args) {
        new MedicamentoGUI();
    }
}
