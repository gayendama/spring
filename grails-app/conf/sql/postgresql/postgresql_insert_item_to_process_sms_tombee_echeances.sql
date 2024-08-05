insert into item_to_process (id, objet_id)
	select public.uuid_generate_v4(), t.id
	from tableau_amortissement as t,
    inner join credit as c on c.id = t.credit_id
    where
      t.credit_id = c.id
      and c.statut_credit in ('VA')
      and c.code_etat = 0
      and t.date_echeance > :dateTraitement
      and t.date_echeance <= :dateTraitementPlusDelai
      and t.statut_echeance = :statutEcheance
    order BY t.credit_id, t.numero_echeance
ON CONFLICT (objet_id) DO NOTHING;