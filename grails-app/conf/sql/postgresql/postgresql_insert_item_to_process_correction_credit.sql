INSERT INTO item_to_process (id, version, objet_id)
    SELECT public.uuid_generate_v4(), 0, id
    from credit_correction
    where statut_correction = 'SA'
ON CONFLICT (objet_id) DO NOTHING;