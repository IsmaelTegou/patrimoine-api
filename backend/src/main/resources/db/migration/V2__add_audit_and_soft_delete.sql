-- Audit Log Table
CREATE TABLE audit_logs (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            action VARCHAR(50) NOT NULL,
                            entity_type VARCHAR(100) NOT NULL,
                            entity_id UUID NOT NULL,
                            user_id UUID,
                            user_email VARCHAR(255),
                            old_values JSONB,
                            new_values JSONB,
                            description VARCHAR(500),
                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            ip_address VARCHAR(45)
);

CREATE INDEX idx_audit_logs_entity_id ON audit_logs(entity_id);
CREATE INDEX idx_audit_logs_entity_type ON audit_logs(entity_type);
CREATE INDEX idx_audit_logs_user_id ON audit_logs(user_id);
CREATE INDEX idx_audit_logs_action ON audit_logs(action);
CREATE INDEX idx_audit_logs_created_at ON audit_logs(created_at DESC);

-- Add soft delete column to all entity tables
ALTER TABLE users ADD COLUMN deleted_at TIMESTAMP;
ALTER TABLE heritage_sites ADD COLUMN deleted_at TIMESTAMP;
ALTER TABLE reviews ADD COLUMN deleted_at TIMESTAMP;
ALTER TABLE media ADD COLUMN deleted_at TIMESTAMP;
ALTER TABLE itineraries ADD COLUMN deleted_at TIMESTAMP;
ALTER TABLE events ADD COLUMN deleted_at TIMESTAMP;
ALTER TABLE visit_history ADD COLUMN deleted_at TIMESTAMP;

-- Add refresh token table
CREATE TABLE refresh_tokens (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                user_id UUID NOT NULL,
                                token_hash VARCHAR(255) NOT NULL UNIQUE,
                                expires_at TIMESTAMP NOT NULL,
                                revoked BOOLEAN DEFAULT false,
                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens(user_id);
CREATE INDEX idx_refresh_tokens_expires_at ON refresh_tokens(expires_at);

-- Add password reset tokens table
CREATE TABLE password_reset_tokens (
                                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                       user_id UUID NOT NULL,
                                       token_hash VARCHAR(255) NOT NULL UNIQUE,
                                       expires_at TIMESTAMP NOT NULL,
                                       used BOOLEAN DEFAULT false,
                                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_password_reset_tokens_user_id ON password_reset_tokens(user_id);
CREATE INDEX idx_password_reset_tokens_expires_at ON password_reset_tokens(expires_at);