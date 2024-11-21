package com.example.myapplication.data

import com.example.myapplication.model.PerguntaModel
import com.example.myapplication.model.RespostaModel

val perguntas = listOf(
    PerguntaModel(
        id = 1,
        tema = "Energia Solar e Sustentabilidade",
        descricao = "Qual é o impacto ambiental da utilização da energia solar em relação aos combustíveis fósseis?",
        alternativas = listOf(
            RespostaModel("Reduz as emissões de gases do efeito estufa", true),
            RespostaModel("Aumenta a poluição do ar", false),
            RespostaModel("Depende exclusivamente de combustíveis fósseis", false),
            RespostaModel("Diminui a eficiência energética global", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 2,
        tema = "Energia Eólica",
        descricao = "Como a energia eólica contribui para a redução das emissões de carbono?",
        alternativas = listOf(
            RespostaModel("Substituindo combustíveis fósseis", true),
            RespostaModel("Gerando calor a partir do vento", false),
            RespostaModel("Reduzindo a dependência de painéis solares", false),
            RespostaModel("Aumentando a queima de carvão", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 3,
        tema = "Eficiência Energética",
        descricao = "Quais são os benefícios de implementar sistemas de eficiência energética em indústrias?",
        alternativas = listOf(
            RespostaModel("Reduzir o consumo de energia e custos", true),
            RespostaModel("Aumentar o desperdício energético", false),
            RespostaModel("Promover o uso de máquinas obsoletas", false),
            RespostaModel("Diminuir a eficiência geral", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 4,
        tema = "Desafios da Mobilidade Elétrica",
        descricao = "Quais são os principais desafios para a implementação de veículos elétricos em massa?",
        alternativas = listOf(
            RespostaModel("Infraestrutura de carregamento", true),
            RespostaModel("Falta de interesse por veículos elétricos", false),
            RespostaModel("Excesso de carregadores públicos", false),
            RespostaModel("Produção em larga escala de combustíveis fósseis", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 5,
        tema = "Acesso Universal à Energia",
        descricao = "Como a energia solar pode ajudar comunidades isoladas a ter acesso à eletricidade?",
        alternativas = listOf(
            RespostaModel("Proporcionando fontes independentes de energia", true),
            RespostaModel("Aumentando a dependência de combustíveis fósseis", false),
            RespostaModel("Reduzindo a demanda por energia elétrica", false),
            RespostaModel("Diminuindo o acesso à tecnologia", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 6,
        tema = "Armazenamento de Energia",
        descricao = "Quais tecnologias de armazenamento são mais promissoras para energias renováveis?",
        alternativas = listOf(
            RespostaModel("Baterias de lítio e hidrogênio", true),
            RespostaModel("Armazenamento em carvão", false),
            RespostaModel("Geração contínua de energia fóssil", false),
            RespostaModel("Queima de resíduos não tratados", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 7,
        tema = "Cibersegurança em Infraestruturas de Energia",
        descricao = "Por que é importante proteger infraestruturas críticas de energia contra ataques cibernéticos?",
        alternativas = listOf(
            RespostaModel("Evitar apagões e danos econômicos", true),
            RespostaModel("Promover vulnerabilidades no sistema", false),
            RespostaModel("Desenvolver infraestruturas mais frágeis", false),
            RespostaModel("Aumentar os riscos de falhas operacionais", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 8,
        tema = "Descarbonização da Indústria",
        descricao = "Qual o papel da descarbonização nas indústrias pesadas para atingir metas climáticas?",
        alternativas = listOf(
            RespostaModel("Reduzir emissões de gases poluentes", true),
            RespostaModel("Aumentar a produção de petróleo", false),
            RespostaModel("Desenvolver novos combustíveis fósseis", false),
            RespostaModel("Promover maior dependência de carvão", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 9,
        tema = "Energia Nuclear Limpa",
        descricao = "Como novas tecnologias nucleares podem ser seguras e sustentáveis?",
        alternativas = listOf(
            RespostaModel("Reduzindo os riscos de resíduos radioativos", true),
            RespostaModel("Aumentando o consumo de urânio", false),
            RespostaModel("Diminuindo a segurança nas operações", false),
            RespostaModel("Eliminando as barreiras de controle ambiental", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 10,
        tema = "Gamificação para Conscientização",
        descricao = "De que forma a gamificação pode educar o público sobre a importância de energias renováveis?",
        alternativas = listOf(
            RespostaModel("Promovendo engajamento e aprendizado", true),
            RespostaModel("Diminuindo o interesse por energias limpas", false),
            RespostaModel("Criando barreiras ao entendimento", false),
            RespostaModel("Aumentando a dependência de combustíveis fósseis", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 11,
        tema = "Impacto das Energias Renováveis",
        descricao = "Quais são os impactos positivos do uso de energias renováveis no meio ambiente?",
        alternativas = listOf(
            RespostaModel("Redução de poluentes atmosféricos", true),
            RespostaModel("Maior dependência de carvão mineral", false),
            RespostaModel("Aumento no consumo de combustíveis fósseis", false),
            RespostaModel("Redução da biodiversidade", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 12,
        tema = "Energia Geotérmica",
        descricao = "Como a energia geotérmica é obtida?",
        alternativas = listOf(
            RespostaModel("Através do calor interno da Terra", true),
            RespostaModel("A partir da combustão de biomassa", false),
            RespostaModel("Por meio da energia das marés", false),
            RespostaModel("Utilizando células solares", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 13,
        tema = "Biocombustíveis",
        descricao = "Qual é a principal matéria-prima para produção de etanol no Brasil?",
        alternativas = listOf(
            RespostaModel("Cana-de-açúcar", true),
            RespostaModel("Petróleo bruto", false),
            RespostaModel("Carvão mineral", false),
            RespostaModel("Areia oleosa", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 14,
        tema = "Eficiência Energética",
        descricao = "Qual medida pode melhorar a eficiência energética em residências?",
        alternativas = listOf(
            RespostaModel("Uso de lâmpadas LED", true),
            RespostaModel("Utilização de lâmpadas incandescentes", false),
            RespostaModel("Manutenção de equipamentos antigos", false),
            RespostaModel("Redução da ventilação natural", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 15,
        tema = "Energia das Ondas",
        descricao = "Como a energia das ondas pode ser convertida em eletricidade?",
        alternativas = listOf(
            RespostaModel("Por meio de dispositivos que capturam o movimento das ondas", true),
            RespostaModel("Através da combustão de biocombustíveis", false),
            RespostaModel("Usando turbinas a vapor convencionais", false),
            RespostaModel("Com painéis solares no fundo do mar", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 16,
        tema = "Mudanças Climáticas",
        descricao = "Como a transição para energias renováveis pode ajudar a combater mudanças climáticas?",
        alternativas = listOf(
            RespostaModel("Reduzindo emissões de gases de efeito estufa", true),
            RespostaModel("Aumentando a temperatura global", false),
            RespostaModel("Promovendo maior dependência de carvão", false),
            RespostaModel("Acelerando o derretimento de calotas polares", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 17,
        tema = "Energia Hidrelétrica",
        descricao = "Qual é a principal fonte de energia hidrelétrica?",
        alternativas = listOf(
            RespostaModel("Água em movimento", true),
            RespostaModel("Energia térmica do subsolo", false),
            RespostaModel("Energia dos ventos", false),
            RespostaModel("Fissão nuclear", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 18,
        tema = "Sistemas de Armazenamento",
        descricao = "Qual tecnologia é mais utilizada para armazenar energia renovável atualmente?",
        alternativas = listOf(
            RespostaModel("Baterias de íon-lítio", true),
            RespostaModel("Geradores a diesel", false),
            RespostaModel("Barragens de carvão", false),
            RespostaModel("Resíduos nucleares", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 19,
        tema = "Impactos Econômicos",
        descricao = "Como as energias renováveis impactam a economia?",
        alternativas = listOf(
            RespostaModel("Criam empregos e impulsionam a inovação", true),
            RespostaModel("Aumentam os custos energéticos em comunidades locais", false),
            RespostaModel("Geram mais gastos com petróleo e gás", false),
            RespostaModel("Reduzem o investimento em novas tecnologias", false)
        ).shuffled()
    ),
    PerguntaModel(
        id = 20,
        tema = "Desafios das Energias Renováveis",
        descricao = "Qual é um dos principais desafios enfrentados pelas energias renováveis?",
        alternativas = listOf(
            RespostaModel("Intermitência na geração de energia", true),
            RespostaModel("Produção ilimitada de energia", false),
            RespostaModel("Dependência completa de combustíveis fósseis", false),
            RespostaModel("Impacto ambiental maior do que combustíveis fósseis", false)
        ).shuffled()
    )
)
