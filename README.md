# LOL Champions Browser

O **LOL Champions Browser** é um aplicativo Android desenvolvido em Kotlin para testar requisições HTTP relacionadas a uma API do jogo *League of Legends*. Ele exibe uma lista de personagens e permite visualizar detalhes específicos de cada campeão. O app utiliza **HttpURLConnection** para realizar as requisições HTTP e consumir a API dos campeões.

## Funcionalidades

- **Tela inicial**: Apresenta uma tela inicial ao abrir o aplicativo.
- **Lista de Campeões**: Exibe uma lista de campeões obtidos via uma requisição HTTP.
- **Detalhes do Campeão**: Ao clicar em um campeão, uma nova tela mostra informações detalhadas sobre ele.

## Tecnologias Utilizadas

- **Kotlin**: Linguagem de programação usada no desenvolvimento.
- **Jetpack Compose**: Framework moderno de UI declarativa utilizado para compor as telas do app.
- **HttpURLConnection**: API nativa usada para realizar as conexões HTTP e obter dados da API de campeões.
- **Material 3**: Para componentes de UI e design responsivo.

## Estrutura do Projeto

O projeto segue uma estrutura de cinco telas principais:

1. **SplashScreen**: Exibida ao abrir o aplicativo.
2. **HomeActivity**: Exibe a escolha de opções das demais telas.
3. **AllChampionsActivity**: Exibe a lista de campeões obtidos via requisição HTTP.
4. **ChampionByTagActivity**: Mostra os detalhes de um campeão específico após clicar na lista.
5. **ChampionsDetailActivity**: Exibe a lista de atributos de um campeão obtidos via requisição HTTP.

### JSON Exemplo
A API retorna os dados dos campeões no formato JSON, como mostrado abaixo:

```json
    {
        "id": "aatrox",
        "key": "266",
        "name": "Aatrox",
        "title": "the Darkin Blade",
        "tags": [
            "Fighter",
            "Tank"
        ],
        "stats": {
            "hp": 580,
            "hpperlevel": 90,
            "mp": 0,
            "mpperlevel": 0,
            "movespeed": 345,
            "armor": 38,
            "armorperlevel": 3.25,
            "spellblock": 32.1,
            "spellblockperlevel": 1.25,
            "attackrange": 175,
            "hpregen": 3,
            "hpregenperlevel": 1,
            "mpregen": 0,
            "mpregenperlevel": 0,
            "crit": 0,
            "critperlevel": 0,
            "attackdamage": 60,
            "attackdamageperlevel": 5,
            "attackspeedperlevel": 2.5,
            "attackspeed": 0.651
        },
        "icon": "http://ddragon.leagueoflegends.com/cdn/10.23.1/img/champion/Aatrox.png",
        "sprite": {
            "url": "http://ddragon.leagueoflegends.com/cdn/10.23.1/img/sprite/champion0.png",
            "x": 0,
            "y": 0
        },
        "description": "Once honored defenders of Shurima against the Void, Aatrox and his brethren would eventually become an even greater threat to Runeterra, and were defeated only by cunning mortal sorcery. But after centuries of imprisonment, Aatrox was the first to find..."
    }
```

## Como Executar o Projeto

### Pré-requisitos
- **Android Studio** instalado.
- Emulador Android configurado ou dispositivo Android físico conectado.

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/usuario/lol-champions-browser.git
   ```
2. Abra o projeto no Android Studio.
3. Compile e execute o aplicativo no emulador ou dispositivo.


## API  
Este projeto consome a API de campeões disponível em:
http://girardon.com.br:3001/champions
