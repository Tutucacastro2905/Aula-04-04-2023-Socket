import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Unisul0404atividade2 extends JFrame implements ActionListener {
    private JTextField enderecoField, portaField;
    private JTextArea respostaArea;

    public Unisul0404atividade2() {
        super("Requisição HTTP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // criação dos componentes gráficos
        JLabel enderecoLabel = new JLabel("Endereço:");
        enderecoField = new JTextField(20);
        JLabel portaLabel = new JLabel("Porta:");
        portaField = new JTextField(5);
        JButton enviarButton = new JButton("Enviar");
        respostaArea = new JTextArea(20, 60);
        respostaArea.setEditable(false);
        
        // adiciona os componentes na janela
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        panel.add(enderecoLabel, c);
        c.gridx = 1;
        panel.add(enderecoField, c);
        c.gridy = 1;
        c.gridx = 0;
        panel.add(portaLabel, c);
        c.gridx = 1;
        panel.add(portaField, c);
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(enviarButton, c);
        JScrollPane scrollPane = new JScrollPane(respostaArea);
        c.gridy = 3;
        panel.add(scrollPane, c);
        add(panel);
        
        // adiciona um listener para o botão
        enviarButton.addActionListener(this);
        
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new Unisul0404atividade2();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // cria o socket e envia a requisição HTTP
            Socket sock = new Socket(enderecoField.getText(), Integer.parseInt(portaField.getText()));
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out.println("GET / HTTP/1.0\n");
            
            // lê a resposta do servidor e exibe na área de texto
            String linha="";
            StringBuilder resposta = new StringBuilder();
            while ((linha = in.readLine()) != null) {
                resposta.append(linha);
                resposta.append("\n");
            }
            respostaArea.setText(resposta.toString());
            
            sock.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar com o servidor: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um número válido para a porta.");
        }
    }
}
