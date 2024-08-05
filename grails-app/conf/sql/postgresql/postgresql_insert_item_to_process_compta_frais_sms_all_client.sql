insert into item_to_process (id, version, objet_id)
	select public.uuid_generate_v4(), 0, c.id
	from
	  client c
	WHERE
	  c.code_etat = '0'
	  AND c.transferer = false
	  AND c.code_client != '0'
	  AND c.ind_num_tel_valide = true
ON CONFLICT (objet_id) DO NOTHING;