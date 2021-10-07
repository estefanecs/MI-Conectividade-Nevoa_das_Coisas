package thread;

import controller.RouterController;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Paciente;
import util.RequisicaoHTTP;
import util.RespostaHTTP;

public class ThreadCliente extends Thread {

    private final Socket socket;
    private boolean conectado;
    private HashMap data_base_ref;
    private Paciente paciente;

    public ThreadCliente(Socket cliente, HashMap data_base) {
        this.socket = cliente;
        this.data_base_ref = data_base;

    }

    @Override
    public void run() {
        conectado = true;
        //imprime na tela o IP do cliente
        System.out.println(socket.getInetAddress());
        while (conectado) {
            try {
                //cria uma requisicao a partir do InputStream do cliente
                RequisicaoHTTP requisicao = RequisicaoHTTP.lerRequisicao(socket.getInputStream());
                //se a conexao esta marcada para se mantar viva entao seta keepalive e o timeout
                if (requisicao.isManterViva()) {
                    socket.setKeepAlive(true);
                    socket.setSoTimeout((int) requisicao.getTempoLimite());
                } else {
                    //se nao seta um valor menor suficiente para uma requisicao
                    socket.setSoTimeout(300);
                }
                //abre o arquivo pelo caminho
                File arquivo = new File(requisicao.getRecurso().replaceFirst("/", ""));
                RespostaHTTP resposta;
                resposta = new RespostaHTTP(requisicao.getProtocolo(), "100", "OK");
                //se o caminho foi igual a / entao deve pegar o /index.html
                RouterController routerController = new RouterController();
                Object[] res = routerController.router(requisicao.getRecurso(), requisicao.getMetodo(), requisicao.getBody(), data_base_ref);
                if (res != null) {
                    System.out.println(res[0].toString());
                    System.out.println(res[1].toString());
                    System.out.println(res[2].toString());
                    resposta.setCodigoResposta((String) res[0]);
                    resposta.setMensagem((String) res[1]);
                    resposta.setConteudoResposta(((String) res[2]).getBytes());
                } else {
                    resposta.setMensagem("Not Found");
                    resposta.setCodigoResposta("404");
                }
                //lê todo o conteúdo do arquivo para bytes e gera o conteudo de resposta
                //converte o formato para o GMT espeficicado pelo protocolo HTTP
                String dataFormatada = format.format(new Date());
                //cabeçalho padrão da resposta HTTP/1.1
                resposta.setCabecalho("Date", dataFormatada);
                resposta.setCabecalho("Server", "PBL server/0.1");
                resposta.setCabecalho("Content-Type", "application/json");
                resposta.setCabecalho("Content-Length", resposta.getTamanhoResposta());
                //cria o canal de resposta utilizando o outputStream
                resposta.setSaida(socket.getOutputStream());
                resposta.enviar();
            } catch (IOException ex) {
                //quando o tempo limite terminar encerra a thread
                if (ex instanceof SocketTimeoutException) {
                    try {
                        socket.close();
                    } catch (IOException ex1) {
                        Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } finally {
                try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    conectado = false;
                }
            }

        }
    }

    private SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:Ss z");

}
