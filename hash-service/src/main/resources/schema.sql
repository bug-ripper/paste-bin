CREATE TABLE IF NOT EXISTS hash (
    id BIGSERIAL PRIMARY KEY,
    hash char(8) NOT NULL UNIQUE,
    is_used BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_unused_hash ON hash (is_used) WHERE is_used = FALSE;

drop index idx_unused_hash;
drop table hash;
