insert into item_to_process (id, version, objet_id)
	select public.uuid_generate_v4(), 0, a.client_id
	from
	  sms_service_abonnement a,
	  service_sms b,
	  client c
	where
	  a.service_sms_id = b.id
	  AND a.is_sms = true
	  AND b.is_sms = true
	  AND a.is_annule = false
	  AND b.ind_actif = true
	  AND c.id = a.client_id
	  AND c.ind_num_tel_valide = true
ON CONFLICT (objet_id) DO NOTHING;