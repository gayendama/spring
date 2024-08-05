insert into item_to_process (id, objet_id)
select public.uuid_generate_v4(), ta.id from tableau_amortissement as ta
inner join credit as cr on cr.id = ta.credit_id
    where cr.statut_credit in ('VA','SO','PX')
    and cr.code_etat=0
    and cr.ind_suspension_tombee = false
	and ta.date_echeance <= :dateTraitement
    and ta.statut_echeance = :statutEcheance
    order by cr.numero_credit, ta.numero_echeance
ON CONFLICT (objet_id) DO NOTHING