INSERT INTO item_to_process (id, version, objet_id, second_objet_id)
    select public.uuid_generate_v4(), 0, tam.id, tc.id
    from
        tontine_adhesion_mise as tam
        inner join tontine_adhesion as ta on ta.id = tam.tontine_adhesion_id
        inner join tontine as t on t.id = ta.tontine_id
        inner join tontine_cycle as tc on tc.tontine_id = t.id
    where
        tc.statut = 'O'
        and ta.statut = 'VA'
        and tam.montant_mise > 0
        and ta.id not in (select tontine_adhesion_id from tontine_cycle_mise where tontine_cycle_id = tc.id and montant_mise = tam.montant_mise and ind_compta_commission = false)
ON CONFLICT (objet_id) DO NOTHING;