CREATE TABLE item (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Automatically generate UUID on insert
  name VARCHAR(255),
  price INTEGER,
  description VARCHAR(255),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  created_by VARCHAR(255),
  updated_by VARCHAR(255)
);