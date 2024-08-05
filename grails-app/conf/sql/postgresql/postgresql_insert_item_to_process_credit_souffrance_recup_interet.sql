-- *==========================================*
-- * Sélectionner les dossiers en souffrance non encore soldés *
-- *==========================================*

insert into item_to_process (id, objet_id)
	select public.uuid_generate_v4(), c.id from credit c
	where c.statut_credit in ('VA', 'SO', 'PX', 'CL')
		and c.ind_credit_souffrance = 'CS'
ON CONFLICT (objet_id) DO NOTHING;