
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.table.*;


public class JanelaPrincipal extends JFrame implements Runnable, ActionListener{

    private JLabel lQuantum;
    private JTextField quantum;
    private JLabel lMaxProcessos;
    private JTextField maxProcessos;
    private JButton bNovaTarefa;
    private JButton bIniciar;
    private DefaultTableModel modelo;
    private JTable tabela;
    private JScrollPane barraRolagem;
    private JanelaCriacaoTarefa jct;
    private int vlQuantum;
    int a;
    public int getVlQuantum() {
        return vlQuantum;
    }

    public void setVlQuantum(int vlQuantum) {
        this.vlQuantum = vlQuantum;
    }


    public JanelaPrincipal(){
       setTitle("Escalonador de Tarefas");
       setLayout(null);
       jct = new JanelaCriacaoTarefa();
       jct.setVisible(false);

       lQuantum = new JLabel("Defina o quantum");
       quantum = new JTextField();
       lMaxProcessos = new JLabel("Processos por minuto");
       maxProcessos = new JTextField();
       bNovaTarefa = new JButton("Adicionar Tarefa");
       bIniciar = new JButton("Iniciar");
       modelo = new DefaultTableModel();
       modelo.addColumn("Nome Tarefa");
       modelo.addColumn("Tipo");
       modelo.addColumn("Vida �til");
       modelo.addColumn("Estado");
       tabela = new JTable(modelo);
       barraRolagem = new JScrollPane(tabela);

       lMaxProcessos.setBounds(30, 50, 130, 25);
       maxProcessos.setBounds(165, 50, 50, 25);
       lQuantum.setBounds(30, 80, 100, 25);
       quantum.setBounds(165, 80, 50, 25);
       barraRolagem.setBounds(30,140,440,150);
       bNovaTarefa.setBounds(300,50,150,25);
       bIniciar.setBounds(300,80,150,25);
       bNovaTarefa.addActionListener(this);
       bIniciar.addActionListener(this);
       add(barraRolagem);
       add(bNovaTarefa);
       add(bIniciar);
       add(lQuantum);
       add(quantum);
       add(lMaxProcessos);
       add(maxProcessos);
       setBounds(500, 250, 500,500);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setVisible(true);
    }


    public synchronized void adicionaTarefa(Tarefa tarefa){

       String linha[] = {tarefa.getNome(),tarefa.getTipo(), Integer.toString(tarefa.getVidaUtil()), tarefa.getEstado()};
       modelo.addRow(linha);
    }


    public synchronized void eliminaTarefa(int lin){

    	modelo.removeRow(lin);
    }

    

    public synchronized void atuTarefa(int i){

        modelo.setValueAt(Escalonador.tarefas.get(i).getVidaUtil(), i, 2);
        if (!Escalonador.tarefas.get(i).isIo()){
            modelo.setValueAt(Escalonador.tarefas.get(i).getEstado(), i, 3);   	
        }	
        
    }
    
    public synchronized void atuIO(int i){
        modelo.setValueAt(Escalonador.tarefas.get(i).getEstado(), i, 3);   
    }


    @Override
    public void run() {

        while(true){
            if (modelo.getRowCount() == 0) {
                continue;
            }


            for(int i=0; i < modelo.getRowCount() - 1; i++){

               //SwingUtilities.invokeLater(new MinhaClasse(modelo, Escalonador.tarefas.get(i).getVidaUtil(), i, 2));
               //SwingUtilities.invokeLater(new MinhaClasse2(modelo, Escalonador.tarefas.get(i).getEstado(), i, 3));


               synchronized (modelo) {
                   //modelo.setValueAt(Escalonador.tarefas.get(i).getVidaUtil(), i, 2);
                   //modelo.setValueAt(Escalonador.tarefas.get(i).getEstado(), i, 3);
               }
           }
        }
        }

    @Override
    public void actionPerformed(ActionEvent e) {

        if( e.getSource() == bNovaTarefa){
            if(maxProcessos.getText().isEmpty()){
          	JOptionPane.showMessageDialog(null, "Favor insira a quantidade maxima de processos por minuto");
    	    }else if(Escalonador.getProcessosMinuto() < Integer.parseInt(maxProcessos.getText())) {
    	    	System.out.println("pro" + Integer.parseInt(maxProcessos.getText()));
    	    	System.out.println("pro" + Escalonador.getProcessosMinuto());
                jct.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "Foi atingido o limite m�ximo de processos por minuto");
            }

        }
        if (e.getSource() == bIniciar){
            if(quantum.getText().isEmpty()){
          	JOptionPane.showMessageDialog(null, "Favor insira o valor do Quantum");
    	    }else{
    	        vlQuantum = Integer.parseInt(quantum.getText());
    	    }
        }

    }

    private class MinhaClasse implements Runnable{

        private final DefaultTableModel modelo;
        private int vidaUtil;
        private int ocorrencia;
        private int estado;

        public MinhaClasse(DefaultTableModel modelo, int vidaUtil, int ocorrencia, int estado) {
            this.modelo = modelo;
            this.vidaUtil = vidaUtil;
            this.ocorrencia = ocorrencia;
            this.estado = estado;
        }


        @Override
        public void run() {
            //modelo.setValueAt(vidaUtil, ocorrencia, estado);
        }

    }
    private class MinhaClasse2 implements Runnable{

        private final DefaultTableModel modelo;
        private String estado;
        private int ocorrencia;
        private int newValue;

        public MinhaClasse2(DefaultTableModel modelo, String estado, int ocorrencia, int newValue) {
            this.modelo = modelo;
            this.estado = estado;
            this.ocorrencia = ocorrencia;
            this.newValue = newValue;
        }


        @Override
        public void run() {
            //modelo.setValueAt(estado, ocorrencia, newValue);
        }

    }

}




