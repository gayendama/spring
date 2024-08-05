insert into item_to_process (id, objet_id)
	select public.uuid_generate_v4(), a.id
	from tableau_amortissement as a
		inner join credit as b on b.id = a.credit_id
    where b.statut_credit in ('VA', 'SO', 'PX')
    	and b.ind_credit_souffrance in ('S', 'I')
        and b.code_etat = 0
		and a.date_echeance > :dateTraitement
        and a.statut_echeance = :statutEcheance
    order by b.date_mise_en_place, a.numero_echeance
ON CONFLICT (objet_id) DO NOTHING;