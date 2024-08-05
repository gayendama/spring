INSERT INTO item_to_process (id, objet_id)
    select
        public.uuid_generate_v4(),
        tcm.id
    from
        tontine_cycle_mise as tcm
        inner join tontine_cycle as tc on tc.id = tcm.tontine_cycle_id
        inner join tontine as t on t.id = tc.tontine_id
    where
        tc.statut = :statutOuvert
        and tc.date_fin <= :dateTraitement
        and tcm.montant_commission > 0
        and tcm.ind_compta_commission = false
ON CONFLICT (objet_id) DO NOTHING;