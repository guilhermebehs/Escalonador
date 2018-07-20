
import java.util.logging.Level;
import java.util.logging.Logger;


public class Tarefa extends Thread {
    
    
    private String nome;
    private String tipo;
    private int vidaUtil;
    private String estado;
    private boolean io;
    
    public Tarefa(String nome, String tipo , int vidaUtil){
        
        this.nome = nome;
        this.tipo = tipo;
        this.vidaUtil= vidaUtil;
        this.estado= "Em espera";
        io= false;
        
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    public String getNome() {
        return nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

 
    public String getTipo() {
        return tipo;
    }

 
    public int getVidaUtil() {
        return vidaUtil;
    }

  
    public void setNome(String nome) {
		this.nome = nome;
	}

	public void setVidaUtil(int vidaUtil) {
        this.vidaUtil = vidaUtil;
    }
    
        public boolean isIo() {
        return io;
    }


    public void setIo(boolean io) {
        this.io = io;
    }
    
    
    
    @Override
    public void run(){  
     while(true){
         System.out.println(); 
        if(isIo()){    
            for(int i=0; i < Escalonador.tempoIO; i++){    
                setEstado("I/0: "+String.valueOf(i+1));
                try {
                    Thread.sleep((1000*Escalonador.clock));
                } catch (InterruptedException ex) { }
            }
            setEstado("Em espera");
          }
         setIo(false);
        }
     }        

        
        
    }
                   
    
