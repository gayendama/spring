insert into item_to_process (id, objet_id)
    select public.uuid_generate_v4(), id from credit
    WHERE statut_credit IN ('SO' , 'PX')
        and ((ind_immobilise is null) or (ind_immobilise=false))
        and nbre_jours_retard <= 90
ON CONFLICT (objet_id) DO NOTHING