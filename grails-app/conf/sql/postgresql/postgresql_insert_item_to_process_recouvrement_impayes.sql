insert into item_to_process (id, objet_id)
select public.uuid_generate_v4(), a.id from credit_compte as a
inner join compte as cp on cp.id = a.compte_id
inner join credit as cr on cr.id = a.credit_id
	where a.ind_actif = true
    and a.nature_compte IN ('I', 'IS', 'II')
    and cp.solde_compte < 0
    and cr.remb_impaye_pret = false
    and cr.statut_credit IN ('VA', 'SO', 'PX')
    and cr.code_etat = 0
order by cr.numero_credit
ON CONFLICT (objet_id) DO NOTHING