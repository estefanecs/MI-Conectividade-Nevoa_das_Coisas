O sistema possui 4 projetos:

FOG:
-Necessita do download do broker Mosquitto, antes de executar o projeto é preciso executar o mosquitto. Verificar como é a execução do Mosquitto no seu sistema operacional. 
-É necessário verificar se as bibliotecas do gson e do cliente.mqtt estão adicionadas. As bibliotecas estão na pasta do projeto.

SENSOR:
-Precisa da biblioteca cliente.mqtt e javafaker, que estão na pasta do projeto. 
-Para funcionar corretamente, deve ser executado junto com o projeto FOG.

API:
-Não há necessidade de configuração.

MEDICO:
-Ao executar, é possível selecionar a quantidade de pacientes que deseja listar. Para monitorar um paciente, basta clicar no nome dele.