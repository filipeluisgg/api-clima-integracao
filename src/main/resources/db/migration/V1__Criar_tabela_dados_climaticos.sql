CREATE TABLE IF NOT EXISTS dados_climaticos
(
    id BIGSERIAL PRIMARY KEY,
    local VARCHAR(100) NOT NULL,
    dia DATE NOT NULL,
    longitude DECIMAL(11, 7) NOT NULL,
    latitude DECIMAL(11, 7) NOT NULL,
    condicao_clima VARCHAR(100),
    descricao_clima VARCHAR(255),
    temperatura_atual DECIMAL(5, 2) NOT NULL,
    temperatura_minima DECIMAL(5, 2),
    temperatura_maxima DECIMAL(5, 2),
    umidade INTEGER,
    data_consulta TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Adiciona um index na coluna 'local' para melhor desempenho na busca de dados
CREATE UNIQUE INDEX IF NOT EXISTS idx_local_dia_unico ON dados_climaticos (local, dia);
