CREATE OR REPLACE FUNCTION update_timestamp() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  BEGIN
    NEW.update_time = current_timestamp(3);
    RETURN NEW;
  END;
$$;

DROP TABLE IF EXISTS ds_user;
CREATE TABLE ds_user(
    user_id  SERIAL PRIMARY KEY,
    username VARCHAR(255) NULL DEFAULT NULL,
    password VARCHAR(255) NULL DEFAULT NULL,
    roles    JSONB NOT NULL DEFAULT ('["ROLE_USER"]'),
    create_time TIMESTAMP(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    update_time TIMESTAMP(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    CONSTRAINT  username_unique_idx UNIQUE(username)
 );

CREATE TRIGGER ds_user_update_time BEFORE UPDATE ON ds_user FOR EACH ROW EXECUTE PROCEDURE update_timestamp();
COMMENT ON TABLE ds_user IS '用户表';
COMMENT ON COLUMN ds_user.user_id IS '用户id';
COMMENT ON COLUMN ds_user.username IS '用户名';
COMMENT ON COLUMN ds_user.password IS '用户登录密码';
COMMENT ON COLUMN ds_user.roles IS '用户角色';
COMMENT ON COLUMN ds_user.create_time IS '创建时间';
COMMENT ON COLUMN ds_user.update_time IS '更新时间';

INSERT INTO ds_user (username, roles, password)
VALUES ('admin', '["ROLE_ADMIN","ROLE_USER"]', 'e10adc3949ba59abbe56e057f20f883e');
