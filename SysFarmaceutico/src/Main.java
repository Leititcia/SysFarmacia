import db.ConexaoBancoDeDados;
import models.Cliente;
import models.Pessoa;
import view.ClienteGUI;
import view.FornecedorGUI;
import view.MedicamentoGUI;
import view.VendaGUI;

import java.sql.Connection;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Main.Tela();
        //Main.TesteDB();
    }

    static void TesteDB(){
        Connection connection = ConexaoBancoDeDados.getConnection();

        if (connection != null) {
            System.out.println("Conexão bem-sucedida!");
            // trabalhar com o banco de dados.
        } else {
            System.out.println("Falha na conexão!");
        }
    }

    static void Tela() {
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Sistema Farmacêutico");
            mainFrame.setSize(1000, 800);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null);

            JPanel menuPanel = new JPanel();
            menuPanel.setLayout(new GridLayout(0, 1));

            JButton btnCliente = new JButton("Clientes");
            JButton btnFornecedor = new JButton("Fornecedores");
            JButton btnMedicamento = new JButton("Medicamentos");
            JButton btnVenda = new JButton("Vendas");

            Dimension buttonSize = new Dimension(200, 60);
            btnCliente.setPreferredSize(buttonSize);
            btnFornecedor.setPreferredSize(buttonSize);
            btnMedicamento.setPreferredSize(buttonSize);
            btnVenda.setPreferredSize(buttonSize);

            menuPanel.add(btnCliente);
            menuPanel.add(btnFornecedor);
            menuPanel.add(btnMedicamento);
            menuPanel.add(btnVenda);

            JPanel avisoPanel = new JPanel();
            avisoPanel.setLayout(new GridBagLayout());

            JLabel avisoLabel1 = new JLabel("Olá, Funcionário!");
            JLabel avisoLabel2 = new JLabel("Tenha acesso às funcionalidades que o Sistema FarmaLab oferece para você!");

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(10, 0, 10, 0);
            gbc.anchor = GridBagConstraints.CENTER;
            avisoPanel.add(avisoLabel1, gbc);

            gbc.gridy = 1;
            avisoPanel.add(avisoLabel2, gbc);

            mainFrame.setLayout(new BorderLayout());
            mainFrame.getContentPane().add(menuPanel, BorderLayout.WEST);
            mainFrame.getContentPane().add(avisoPanel, BorderLayout.CENTER);

            mainFrame.setVisible(true);


            btnCliente.addActionListener(e -> {
                ClienteGUI clienteGUI = new ClienteGUI();
                clienteGUI.setVisible(true);
                mainFrame.dispose();
            });

            btnFornecedor.addActionListener(e -> {
                FornecedorGUI fornecedorGUI = new FornecedorGUI();
                fornecedorGUI.setVisible(true);
                mainFrame.dispose();
            });

            btnMedicamento.addActionListener(e -> {
                MedicamentoGUI medicamentoGUI = new MedicamentoGUI();
                medicamentoGUI.setVisible(true);
                mainFrame.dispose();
            });

            btnVenda.addActionListener(e -> {
                VendaGUI vendaGUI = new VendaGUI();
                vendaGUI.setVisible(true);
                mainFrame.dispose();
            });
        });
    }
}
