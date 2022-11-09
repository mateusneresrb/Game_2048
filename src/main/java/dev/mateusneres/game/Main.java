package dev.mateusneres.game;

import dev.mateusneres.game.models.Game;
import dev.mateusneres.game.utils.Util;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Esta classe NÃO DEVE SER MODIFICADA, EXCETO pela linha 28.
 * @author Professor Gabriel de Carvalho
 */
public class Main {

    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        int entrada;
        Game jogo = null; /// Aqui deve ser instanciado o seu jogo. substitua essa linha pelo seu jogo
        ///Ex. Game jogo = new MeuJogo();

        while(true){
            try{
                Util.clearScreen();
                System.out.println("Escolha uma das opções abaixo (digite somente o número):");
                System.out.println("1- Novo jogo");
                System.out.println("2- Carregar jogo");
                System.out.println("3- Sair");
                entrada = teclado.nextInt();

                switch(entrada){
                    case 1:
                        jogo.criaNovo();
                        break;
                    case 2:
                        jogo.carrega();
                        break;
                    case 3:
                        jogo.sai();
                        break;
                    default:
                        throw new InputMismatchException("entrada inválida");
                }
                jogo.executa();
                break;
            }catch(InputMismatchException ex){
                if(ex != null) teclado.nextLine();
                System.err.println("Entrada inválida. Deve ser um inteiro entre 1 e 3. Aperte ENTER para fechar");
                teclado.nextLine();
            } catch (IOException ex) {
                System.out.println("IOException: Erro indefinido ao tentar limpar a tela do console");
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException: Erro indefinido ao tentar limpar a tela do console");
            }
        }
    }

}