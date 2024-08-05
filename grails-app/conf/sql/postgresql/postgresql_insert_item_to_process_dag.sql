insert into item_to_process (id, objet_id)
	select public.uuid_generate_v4(), d.id
	from depot_terme d, credit c, credit_compte cc, type_epargne_terme t
	where d.credit_id = c.id
		and cc.credit_id = c.id
		and cc.nature_Compte= 'E'
		and d.type_Epargne_Terme_id = t.id
		and d.compte_Epargne_id = cc.compte_id
		and c.date_Cloture <= :dateTraitement
		and d.statut_Dat = 'VA'
		and t.type_Epargne_Terme = 'GA'
		and c.statut_Credit = 'CL'
ON CONFLICT (objet_id) DO NOTHING;