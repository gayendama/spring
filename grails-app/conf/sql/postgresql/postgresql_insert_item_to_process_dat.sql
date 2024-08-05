insert into item_to_process (id, objet_id)
	select public.uuid_generate_v4(), d.id
	from depot_terme d,type_epargne_terme t
	where d.date_fin_dat <=:dateTraitement
	    and d.statut_dat = 'VA'
	    and d.type_epargne_terme_id = t.id
	    and t.type_epargne_terme != 'GA'
ON CONFLICT (objet_id) DO NOTHING;