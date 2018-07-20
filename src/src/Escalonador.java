
import java.awt.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;



public class Escalonador {

    static int clock;
    private int quantum=0;
    static int tempoIO;
    static int processosMinuto;
    private int segundos;
    static ArrayList<Tarefa> tarefas = new ArrayList<>();
    private JanelaPrincipal janela;
    private Thread t;
    private int qtdTarefa=0;
    public Escalonador(int clock, int tempoIO){

        this.clock=clock;
        janela = new JanelaPrincipal();
        t = new Thread(janela);
        this.tempoIO = tempoIO;
        this.processosMinuto = 0;
        this.segundos = 0;
    }

    public void criaTarefa(String nome, String tipo, int vidaUtil){

        //if (processosMinuto == )
        Tarefa tarefa = new Tarefa(nome, tipo, vidaUtil);
        if(tipo.equalsIgnoreCase("I/O Bound"))
           tarefa.start();
        tarefas.add(tarefa);
        janela.adicionaTarefa(tarefa);
        processosMinuto ++;
        qtdTarefa ++;
    }

    public void controleTarefaPorMinuto(){
        if (segundos < 60){
            segundos++;
        } else{
            segundos = 0;
            processosMinuto = 0;
        }
    }


    public synchronized void iniciar(){
        Tarefa tarefaAux = new Tarefa("Aux", "Aux", 0);
        while(janela.getVlQuantum()== 0){
            System.out.println();
        }
        t.start();
        while(true){
          setQuantum(janela.getVlQuantum());
          for(int y = 0; y < tarefas.size(); y++){
            for(int i=0; i < quantum && tarefas.get(y).getVidaUtil() > 0 && !tarefas.get(y).isIo(); i++){
                tarefas.get(y).setVidaUtil(tarefas.get(y).getVidaUtil()-1);
                
                tarefas.get(y).setEstado("Executando");
                janela.atuTarefa(y);
                
                
                try {
                    Thread.sleep(clock*1000);
                    controleTarefaPorMinuto();

                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null,"caiu aqui");
                }
                
                if(tarefas.get(y).getTipo().equalsIgnoreCase("I/O Bound") && i +1 == quantum){
                    tarefas.get(y).setIo(true);
                 }
                
                for(int a = 0; a < tarefas.size(); a++){
                	if (tarefas.get(a).isIo()){
                		janela.atuIO(a);   	
                    }
                }
            }
            if(tarefas.get(y).getVidaUtil() == 0){
                janela.eliminaTarefa(y);
                tarefas.remove(y);
            	y--;
                continue;
            }
            if(!tarefas.get(y).isIo()){
               tarefas.get(y).setEstado("Em espera");
          	}

            if (tarefas.size() > 1){
                tarefaAux = tarefas.get(y);
                janela.eliminaTarefa(y);
                tarefas.remove(y);
                tarefas.add(tarefaAux);
                janela.adicionaTarefa(tarefaAux);
                try {
                    Thread.sleep(clock*300);

                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null,"caiu aqui");
                }
                
            }

            for(int a = 0; a < tarefas.size(); a++){
            	
            	janela.atuIO(a);   	
                
            }
            
            
            y--;


          }
          System.out.println("llegaste here");
        }
        
        

    }
    
    

    public int getQuantum() {
	return quantum;
    }

    public void setQuantum(int quantum) {
	this.quantum = quantum;
    }

    public static int getProcessosMinuto() {
        return processosMinuto;
    }

    public static void setProcessosMinuto(int processosMinuto) {
        Escalonador.processosMinuto = processosMinuto;
    }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }


    private class MinhaClasse implements Runnable{

        private final JanelaPrincipal janela;
        private int ocorrencia;

        public MinhaClasse(JanelaPrincipal janela, int ocorrencia) {
            this.janela = janela;
            this.ocorrencia = ocorrencia;
        }

        @Override
        public void run() {
            janela.eliminaTarefa(ocorrencia);
        }

    }
    private class MinhaClasse2 implements Runnable{

        private final JanelaPrincipal janela;
        private Tarefa tarefa;

        public MinhaClasse2(JanelaPrincipal janela, Tarefa tarefa) {
            this.janela = janela;
            this.tarefa = tarefa;
        }

        @Override
        public void run() {
            janela.adicionaTarefa(tarefa);
        }

    }



}
