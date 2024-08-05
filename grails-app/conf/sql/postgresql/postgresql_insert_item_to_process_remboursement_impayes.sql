insert into item_to_process (id, objet_id)
    select public.uuid_generate_v4(), a.id from credit_compte as a
        inner join compte as b on b.id = a.compte_id
        inner join credit as c on c.id = a.credit_id
    where a.ind_actif = true
        and a.nature_compte IN ('I', 'IS', 'II')
        and b.solde_compte < 0
        and c.statut_credit IN ('VA', 'SO', 'PX')
        and c.code_etat = 0
        order by c.numero_credit
ON CONFLICT (objet_id) DO NOTHING;