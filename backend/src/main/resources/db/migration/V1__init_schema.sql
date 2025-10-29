-- Flyway Database Migration - Initial Schema
-- Heritage Management Platform
-- =============================================

-- =============================================
-- Drop existing objects (if any) for clean start
-- =============================================
DROP TABLE IF EXISTS visit_history CASCADE;
DROP TABLE IF EXISTS reports CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS itinerary_sites CASCADE;
DROP TABLE IF EXISTS itineraries CASCADE;
DROP TABLE IF EXISTS reviews CASCADE;
DROP TABLE IF EXISTS media CASCADE;
DROP TABLE IF EXISTS heritage_sites CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- =============================================
-- Enums / Types
-- =============================================
CREATE TYPE user_role AS ENUM (
    'ANONYMOUS_VISITOR',
    'TOURIST',
    'GUIDE',
    'SITE_MANAGER',
    'ADMINISTRATOR'
);

CREATE TYPE language_enum AS ENUM (
    'FRENCH',
    'ENGLISH',
    'FULANI'
);

CREATE TYPE heritage_type_enum AS ENUM (
    'NATURAL_SITE',
    'NATIONAL_RESERVE',
    'NATIONAL_PARK',
    'CHIEFDOM',
    'PALACE',
    'HISTORICAL_SITE',
    'MUSEUM',
    'RITUAL',
    'DANCE',
    'TRADITIONAL_CRAFT',
    'FESTIVAL',
    'ARCHAEOLOGICAL_SITE'
);

CREATE TYPE conservation_state_enum AS ENUM (
    'GOOD',
    'FAIR',
    'DEGRADED'
);

CREATE TYPE media_type_enum AS ENUM (
    'PHOTO',
    'VIDEO'
);

CREATE TYPE report_type_enum AS ENUM (
    'VISITATION',
    'REVIEWS',
    'USER_ENGAGEMENT'
);

-- =============================================
-- USERS Table
-- =============================================
CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       phone_number VARCHAR(20),
                       role user_role NOT NULL DEFAULT 'ANONYMOUS_VISITOR',
                       language language_enum NOT NULL DEFAULT 'FRENCH',
                       is_active BOOLEAN NOT NULL DEFAULT true,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       created_by VARCHAR(100),
                       updated_at TIMESTAMP,
                       updated_by VARCHAR(100),
                       CONSTRAINT email_format CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
    );

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_users_is_active ON users(is_active);
CREATE INDEX idx_users_created_at ON users(created_at DESC);

-- =============================================
-- HERITAGE_SITES Table
-- =============================================
CREATE TABLE heritage_sites (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                site_name VARCHAR(255) NOT NULL,
                                description TEXT,
                                heritage_type heritage_type_enum NOT NULL,
                                latitude DECIMAL(10, 8),
                                longitude DECIMAL(11, 8),
                                province VARCHAR(100),
                                opening_time TIME,
                                closing_time TIME,
                                entry_fee DECIMAL(10, 2) DEFAULT 0,
                                max_capacity INTEGER,
                                conservation_state conservation_state_enum NOT NULL DEFAULT 'GOOD',
                                manager_contact_name VARCHAR(150),
                                manager_phone_number VARCHAR(20),
                                average_rating DECIMAL(3, 2) DEFAULT 0,
                                total_reviews INTEGER DEFAULT 0,
                                total_visits INTEGER DEFAULT 0,
                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                created_by VARCHAR(100),
                                updated_at TIMESTAMP,
                                updated_by VARCHAR(100)
);

CREATE INDEX idx_heritage_sites_heritage_type ON heritage_sites(heritage_type);
CREATE INDEX idx_heritage_sites_province ON heritage_sites(province);
CREATE INDEX idx_heritage_sites_created_at ON heritage_sites(created_at DESC);
CREATE INDEX idx_heritage_sites_average_rating ON heritage_sites(average_rating DESC);

-- =============================================
-- MEDIA Table
-- =============================================
CREATE TABLE media (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       heritage_site_id UUID NOT NULL,
                       media_type media_type_enum NOT NULL,
                       file_name VARCHAR(255) NOT NULL,
                       minio_path VARCHAR(500) NOT NULL,
                       file_size BIGINT NOT NULL,
                       description TEXT,
                       access_url VARCHAR(500),
                       uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (heritage_site_id) REFERENCES heritage_sites(id) ON DELETE CASCADE
);

CREATE INDEX idx_media_heritage_site_id ON media(heritage_site_id);
CREATE INDEX idx_media_media_type ON media(media_type);
CREATE INDEX idx_media_uploaded_at ON media(uploaded_at DESC);

-- =============================================
-- REVIEWS Table
-- =============================================
CREATE TABLE reviews (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         heritage_site_id UUID NOT NULL,
                         user_id UUID NOT NULL,
                         rating INTEGER NOT NULL,
                         comment TEXT,
                         is_approved BOOLEAN NOT NULL DEFAULT false,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP,
                         FOREIGN KEY (heritage_site_id) REFERENCES heritage_sites(id) ON DELETE CASCADE,
                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                         CONSTRAINT rating_range CHECK (rating >= 1 AND rating <= 5)
);

CREATE INDEX idx_reviews_heritage_site_id ON reviews(heritage_site_id);
CREATE INDEX idx_reviews_user_id ON reviews(user_id);
CREATE INDEX idx_reviews_is_approved ON reviews(is_approved);
CREATE INDEX idx_reviews_created_at ON reviews(created_at DESC);

-- =============================================
-- ITINERARIES Table
-- =============================================
CREATE TABLE itineraries (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             title VARCHAR(255) NOT NULL,
                             description TEXT,
                             estimated_duration INTEGER,
                             theme VARCHAR(100),
                             created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP,
                             created_by VARCHAR(100),
                             updated_by VARCHAR(100)
);

CREATE INDEX idx_itineraries_created_at ON itineraries(created_at DESC);
CREATE INDEX idx_itineraries_theme ON itineraries(theme);

-- =============================================
-- ITINERARY_SITES Junction Table (Many-to-Many)
-- =============================================
CREATE TABLE itinerary_sites (
                                 itinerary_id UUID NOT NULL,
                                 heritage_site_id UUID NOT NULL,
                                 site_order INTEGER NOT NULL,
                                 PRIMARY KEY (itinerary_id, heritage_site_id),
                                 FOREIGN KEY (itinerary_id) REFERENCES itineraries(id) ON DELETE CASCADE,
                                 FOREIGN KEY (heritage_site_id) REFERENCES heritage_sites(id) ON DELETE CASCADE
);

CREATE INDEX idx_itinerary_sites_order ON itinerary_sites(itinerary_id, site_order);

-- =============================================
-- EVENTS Table
-- =============================================
CREATE TABLE events (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        heritage_site_id UUID NOT NULL,
                        event_title VARCHAR(255) NOT NULL,
                        event_description TEXT,
                        event_type VARCHAR(100),
                        event_location VARCHAR(255),
                        start_date TIMESTAMP NOT NULL,
                        end_date TIMESTAMP NOT NULL,
                        max_capacity INTEGER,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP,
                        FOREIGN KEY (heritage_site_id) REFERENCES heritage_sites(id) ON DELETE CASCADE,
                        CONSTRAINT date_logic CHECK (end_date >= start_date)
);

CREATE INDEX idx_events_heritage_site_id ON events(heritage_site_id);
CREATE INDEX idx_events_start_date ON events(start_date);
CREATE INDEX idx_events_event_type ON events(event_type);

-- =============================================
-- VISIT_HISTORY Table
-- =============================================
CREATE TABLE visit_history (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               user_id UUID NOT NULL,
                               heritage_site_id UUID NOT NULL,
                               visit_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               visit_duration INTEGER,
                               access_source VARCHAR(100),
                               FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                               FOREIGN KEY (heritage_site_id) REFERENCES heritage_sites(id) ON DELETE CASCADE
);

CREATE INDEX idx_visit_history_user_id ON visit_history(user_id);
CREATE INDEX idx_visit_history_heritage_site_id ON visit_history(heritage_site_id);
CREATE INDEX idx_visit_history_visit_date ON visit_history(visit_date DESC);

-- =============================================
-- REPORTS Table
-- =============================================
CREATE TABLE reports (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         report_type report_type_enum NOT NULL,
                         report_title VARCHAR(255) NOT NULL,
                         start_date TIMESTAMP NOT NULL,
                         end_date TIMESTAMP NOT NULL,
                         report_data JSONB,
                         generated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         generated_by UUID NOT NULL,
                         FOREIGN KEY (generated_by) REFERENCES users(id) ON DELETE SET NULL
);

CREATE INDEX idx_reports_report_type ON reports(report_type);
CREATE INDEX idx_reports_generated_at ON reports(generated_at DESC);
CREATE INDEX idx_reports_generated_by ON reports(generated_by);

-- =============================================
-- AUDIT TRIGGERS
-- =============================================

-- Trigger to update updated_at timestamp on users
CREATE OR REPLACE FUNCTION update_users_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER users_update_timestamp
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_users_updated_at();

-- Trigger to update updated_at timestamp on heritage_sites
CREATE OR REPLACE FUNCTION update_heritage_sites_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER heritage_sites_update_timestamp
    BEFORE UPDATE ON heritage_sites
    FOR EACH ROW
    EXECUTE FUNCTION update_heritage_sites_updated_at();

-- Trigger to update average_rating on heritage_sites when review is updated
CREATE OR REPLACE FUNCTION update_site_rating()
RETURNS TRIGGER AS $$
BEGIN
UPDATE heritage_sites
SET average_rating = (
    SELECT COALESCE(AVG(rating), 0)
    FROM reviews
    WHERE heritage_site_id = NEW.heritage_site_id AND is_approved = true
),
    total_reviews = (
        SELECT COUNT(*)
        FROM reviews
        WHERE heritage_site_id = NEW.heritage_site_id AND is_approved = true
    )
WHERE id = NEW.heritage_site_id;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER reviews_update_site_rating
    AFTER INSERT OR UPDATE OR DELETE ON reviews
    FOR EACH ROW
    EXECUTE FUNCTION update_site_rating();

-- Trigger to increment visit count on heritage_sites
CREATE OR REPLACE FUNCTION increment_visit_count()
RETURNS TRIGGER AS $$
BEGIN
UPDATE heritage_sites
SET total_visits = total_visits + 1
WHERE id = NEW.heritage_site_id;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER visit_history_increment_count
    AFTER INSERT ON visit_history
    FOR EACH ROW
    EXECUTE FUNCTION increment_visit_count();

-- =============================================
-- INITIAL DATA (Optional - for development)
-- =============================================

-- Insert sample admin user (password: admin123)
-- bcrypt hash of "admin123" - you should generate this properly
INSERT INTO users (email, password_hash, first_name, last_name, role, language)
VALUES (
           'admin@patrimoine.local',
           '$2a$10$slYQmyNdGzin7olVN3p5Be7DWgMb54VxMQw7tC9QskFTrWcqLEi.e',  -- bcrypt hash
           'Admin',
           'User',
           'ADMINISTRATOR',
           'FRENCH'
       );

-- =============================================
-- GRANTS (for non-superuser accounts - if needed)
-- =============================================
-- Uncomment and modify for production environment setup
-- GRANT USAGE ON SCHEMA public TO patrimoine_user;
-- GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO patrimoine_user;
-- GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO patrimoine_user;
-- GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA public TO patrimoine_user;

-- =============================================
-- END OF MIGRATION
-- =============================================
COMMIT;