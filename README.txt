O sistema possui 4 projetos:

FOG:
-Necessita do download do broker Mosquitto, antes de executar o projeto � preciso executar o mosquitto. Verificar como � a execu��o do Mosquitto no seu sistema operacional. 
-� necess�rio verificar se as bibliotecas do gson e do cliente.mqtt est�o adicionadas. As bibliotecas est�o na pasta do projeto.

SENSOR:
-Precisa da biblioteca cliente.mqtt e javafaker, que est�o na pasta do projeto. 
-Para funcionar corretamente, deve ser executado junto com o projeto FOG.

API:
-N�o h� necessidade de configura��o.

MEDICO:
-Ao executar, � poss�vel selecionar a quantidade de pacientes que deseja listar. Para monitorar um paciente, basta clicar no nome dele.