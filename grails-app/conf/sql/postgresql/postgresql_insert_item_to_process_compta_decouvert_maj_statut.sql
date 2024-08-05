insert into item_to_process (id, objet_id)
	select public.uuid_generate_v4(), d.id
	from decouvert d
	where d.statut != 'CL'
ON CONFLICT (objet_id) DO NOTHING;