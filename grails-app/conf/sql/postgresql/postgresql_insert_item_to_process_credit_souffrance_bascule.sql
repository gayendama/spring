insert into item_to_process (id, objet_id)
	select public.uuid_generate_v4(), c.id from credit c
	where c.statut_credit in ('VA', 'SO', 'PX')
		and c.nbre_jours_retard > 90
		and c.ind_credit_souffrance is null
	order by c.numero_credit
ON CONFLICT (objet_id) DO NOTHING;