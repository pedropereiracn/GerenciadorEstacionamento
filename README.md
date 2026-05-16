# Sistema de Gerenciamento de Estacionamento

## Integrantes

- Pedro Augusto de Castro Neves Pereira
- Bruno Correa Pscheidt
- João Vitor Dadas

## Contexto

O projeto consiste em um sistema em Java para o gerenciamento operacional de um estacionamento. O sistema simula o dia a dia de um estabelecimento real, contemplando o cadastro de veículos (carros, motos e caminhões), o controle de ocupação das vagas (comuns, cobertas e especiais), a emissão e fechamento de tickets de entrada e saída, o cálculo automático de tarifas conforme o tipo de veículo e o tempo de permanência, e a diferenciação no atendimento entre clientes avulsos e mensalistas.

A tabela de tarifas e a base de clientes mensalistas são carregadas a partir de arquivos CSV, permitindo que o gestor atualize valores e cadastros sem alterar o código-fonte. O histórico de tickets é persistido entre execuções por meio de serialização de objetos, e toda a operação ocorre por meio de uma interface gráfica.

### Divisão inicial de tarefas

- **Pedro Augusto de Castro Neves Pereira** — Modelagem do domínio: hierarquia de Veículos, hierarquia de Vagas e exceções customizadas.
- **Bruno Correa Pscheidt** — Sistema de tickets, cálculo de tarifas, lógica de clientes mensalistas e avulsos, e leitura de arquivos CSV.
- **João Vitor Dadas** — Interface gráfica e persistência de dados via serialização de objetos.
